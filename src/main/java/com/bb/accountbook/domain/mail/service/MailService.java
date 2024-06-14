package com.bb.accountbook.domain.mail.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.domain.mail.MailSender;
import com.bb.accountbook.domain.mail.repository.MailRepository;
import com.bb.accountbook.entity.Mail;
import com.bb.accountbook.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MailService {

    private final MailRepository mailRepository;

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    @Value("${aws.ses.sender}")
    private String sender;

    public Long createMail(User receiver, Integer ttl) {
        Mail mail = new Mail(receiver, ttl);
        return mailRepository.save(mail).getId();
    }

    public void send(String subject, String content, List<String> receivers) {
        MailSender mailSender = new MailSender(sender, receivers, subject, content);

        SendEmailResult sendEmailResult = amazonSimpleEmailService.sendEmail(mailSender.getSendEmailRequest());

        if (sendEmailResult.getSdkHttpMetadata().getHttpStatusCode() != 200) {
            log.error(sendEmailResult.getSdkHttpMetadata().toString());
            throw new GlobalException(ErrorCode.ERR_MAIL_000);
        }
    }

    public boolean sendIdentityVerificationEmail(User receiver, Integer ttl) {
        /**
         * 1. mail entity 생성
         */
        Long mailId = createMail(receiver, ttl);

        /**
         * 2. mail 정보 set
         */
        String subject = "본인 인증 메일입니다.";
        String content = "<h1>" + mailId + "</h1>";
        List<String> receivers = List.of(receiver.getEmail());

        /**
         * 3. AWS SES 메일 발송
         */
        send(subject, content, receivers);

        return true;
    }

}
