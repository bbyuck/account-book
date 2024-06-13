package com.bb.accountbook.domain.user.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.domain.user.repository.RoleRepository;
import com.bb.accountbook.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Test
    @DisplayName("회원가입")
    public void join1() throws Exception {
        // given
        String email = "joinTest1@naver.com";
        String password = "pass1";

        // when
        Long joinedUserId = userService.signup(email, password);
        User joinedUser = userService.findUserById(joinedUserId);

        // then
        assertThat(joinedUser.getEmail()).isEqualTo(email);
        assertThat(passwordEncoder.matches(password, joinedUser.getPassword())).isTrue();
    }

    @Test
    @DisplayName("중복 가입 방지")
    public void join2() throws Exception {
        // given
        String email = "k941026h@naver.com";
        String password = "pass1";

        // when
        assertThrows(GlobalException.class, () -> userService.signup(email, password), ErrorCode.ERR_USR_001.getValue());

        // then
    }
}