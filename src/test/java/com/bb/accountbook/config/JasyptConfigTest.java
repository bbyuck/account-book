package com.bb.accountbook.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class JasyptConfigTest {

    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        /**
         * 난 속죄할 것이오
         * 난 속죄할 것이오
         * 난 속죄할 것이오
         * 난 속죄할 것이오
         * 난 속죄할 것이오
         * 난 속죄할 것이오
         */
        return encryptor;
    }

//    @Test
    void encrypt() {
        /**
         * 난 속죄할 것이오
         * 난 속죄할 것이오
         * 난 속죄할 것이오
         * 난 속죄할 것이오
         * 난 속죄할 것이오
         * 난 속죄할 것이오
         */
    }


}