package com.bb.accountbook.domain.mail.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.bb.accountbook.common.exception.GlobalCheckedException;
import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.common.util.RSACrypto;
import com.bb.accountbook.domain.mail.MailSender;
import com.bb.accountbook.domain.mail.repository.MailRepository;
import com.bb.accountbook.entity.Mail;
import com.bb.accountbook.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.bb.accountbook.common.model.codes.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MailService {

    private final MailRepository mailRepository;

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    private final RSACrypto rsaCrypto;

    @Value("${aws.ses.sender}")
    private String sender;

    @Value("${aws.ses.use}")
    private boolean sesUse;

    public Long createMail(User receiver, Integer ttl) {
        Mail mail = new Mail(receiver, ttl);
        return mailRepository.save(mail).getId();
    }

    public void send(String subject, String content, List<String> receivers) throws GlobalCheckedException {
        MailSender mailSender = new MailSender(sender, receivers, subject, content);

        SendEmailResult sendEmailResult = amazonSimpleEmailService.sendEmail(mailSender.getSendEmailRequest());

        if (sendEmailResult.getSdkHttpMetadata().getHttpStatusCode() != 200) {
            log.error(sendEmailResult.getSdkHttpMetadata().toString());
            throw new GlobalCheckedException(ErrorCode.ERR_MAIL_000);
        }
    }

    public boolean sendIdentityVerificationEmail(User receiver, Integer ttl) throws GlobalCheckedException {
        /**
         * TODO - 메일 조회해서 마지막으로 수신한 메일이 3분 이내에 있으면 발송하지 않음
         */

        /**
         * 1. mail entity 생성
         */
        Long mailId = createMail(receiver, ttl);

        /**
         * 2. mail 정보 set
         */
        String subject = "본인 인증 메일입니다.";
        String content = createIdentityVerificationEmailContent(mailId);
        List<String> receivers = List.of(receiver.getEmail());


        /**
         * 3. AWS SES 메일 발송
         */
        send(subject, content, receivers);

        Mail mail = mailRepository.findById(mailId).orElseThrow(() -> {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "sendIdentityVerificationEmail", mailId, ERR_MAIL_002.getValue());
            log.debug("메일 발송 후 data 처리 중 에러 발생");
            return new GlobalCheckedException(ERR_MAIL_002);
        });
        mail.changeStatusToSent();

        return true;
    }

    private String createIdentityVerificationEmailContent(Long mailId) {
        // HTML 파일의 경로를 지정.
        String filePath = "static/email/verify_email_form.html";

        // Spring의 ResourceLoader를 사용하여 리소스 read.
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + filePath);
            }

            // HTML 파일 내용을 String으로 변환합니다.
            String htmlContent = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            // ${verificationUrl} 플레이스홀더를 실제 URL로 대체합니다.
            return htmlContent.replace("${verificationUrl}", createVerifyApiURL(mailId));
        }
        catch(IOException e) {
            log.debug(e.getMessage(), e);
            throw new GlobalException(ErrorCode.ERR_MAIL_001);
        }
    }

    public String createVerifyApiURL(Long mailId) {
        try {
            return "https://booroute.com/verify?target=" + rsaCrypto.encrypt(String.valueOf(mailId));
        }
        catch(Exception e) {
            log.debug(e.getMessage(), e);
            log.debug("Verify API URL을 만들지 못했습니다.");
            throw new GlobalException(ERR_MAIL_000);
        }
    }

    @Transactional(readOnly = true)
    public Mail findMailById(Long mailId) {
        return mailRepository.findById(mailId).orElseThrow(() -> {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "findMailById", mailId, ERR_MAIL_002.getValue());
            return new GlobalException(ERR_MAIL_002);
        });
    }

    public boolean canUse() {
        return sesUse;
    }
}
