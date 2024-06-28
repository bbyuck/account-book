package com.bb.accountbook;

import com.bb.accountbook.domain.user.repository.UserRepository;
import com.bb.accountbook.domain.user.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountBookApplicationTests {


    @Test
    void contextLoads() {

    }



}
