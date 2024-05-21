package com.bb.accountbook.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailSender {

    private final String SENDER_EMAIL;
    private final String SENDER_PASSWORD;

    public MailSender(@Value("email.official.email") String email, @Value("email.official.password") String password) {
        SENDER_EMAIL = email;
        SENDER_PASSWORD = password;
    }

}
