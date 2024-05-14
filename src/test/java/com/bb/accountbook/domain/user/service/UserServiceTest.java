package com.bb.accountbook.domain.user.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.common.model.codes.GenderCode;
import com.bb.accountbook.domain.user.repository.RoleRepository;
import com.bb.accountbook.domain.user.repository.UserRoleRepository;
import com.bb.accountbook.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleRepository roleRepository;


    @Test
    @DisplayName("회원가입")
    public void join1() throws Exception {
        // given
        String email = "joinTest1@naver.com";
        String password = "pass1";
        GenderCode gender = GenderCode.M;

        // when
        Long joinedUserId = userService.join(email, password, gender);
        User joinedUser = userService.findUserById(joinedUserId);

        // then
        assertThat(joinedUser.getEmail()).isEqualTo(email);
        assertThat(joinedUser.getPassword()).isEqualTo(password);
        assertThat(joinedUser.getGender()).isEqualTo(gender);
    }

    @Test
    @DisplayName("중복 가입 방지")
    public void join2() throws Exception {
        // given
        String email = "k941026h@naver.com";
        String password = "pass1";
        GenderCode gender = GenderCode.M;

        // when
        assertThrows(GlobalException.class, () -> userService.join(email, password, gender), ErrorCode.ERR_USR_001.getValue());

        // then
    }

}