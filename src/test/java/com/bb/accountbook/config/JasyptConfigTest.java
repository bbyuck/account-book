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
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword("accountBookDbKey"); // 암호화키
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256"); // 알고리즘
        config.setKeyObtentionIterations(1000); // 반복할 해싱 회수
        config.setPoolSize("1"); // 인스턴스 pool
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator"); // salt 생성 클래스
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64"); //인코딩 방식
        encryptor.setConfig(config);
        return encryptor;
    }

    @Test
    void encrypt() {
        String id = stringEncryptor().encrypt("APP");
        String pw = stringEncryptor().encrypt("1q2w3e4r5T!@#");
        String url = stringEncryptor().encrypt("jdbc:oracle:thin:@booroutedb_medium?TNS_ADMIN=/app/account-book-api/key/Wallet_BOOROUTEDB");

        System.out.println("id = " + id);
        System.out.println("pw = " + pw);
        System.out.println("url = " + url);
    }


}