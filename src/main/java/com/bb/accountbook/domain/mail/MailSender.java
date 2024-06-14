package com.bb.accountbook.domain.mail;

import com.amazonaws.services.simpleemail.model.*;
import lombok.Getter;

import java.util.List;

/**
 * @param receivers 받는 사람
 * @param subject   제목
 * @param content   본문 내용
 */
public record MailSender(String sender, List<String> receivers, String subject, String content) {
    public SendEmailRequest getSendEmailRequest() {
        Destination destination = new Destination().withToAddresses(receivers);

        Message message = new Message()
                .withSubject(createContent(this.subject))
                .withBody(new Body().withHtml(createContent(this.content)));

        return new SendEmailRequest()
                .withSource(sender)
                .withDestination(destination)
                .withMessage(message);
    }

    private Content createContent(String text) {
        return new Content().withCharset("UTF-8").withData(text);
    }

}
