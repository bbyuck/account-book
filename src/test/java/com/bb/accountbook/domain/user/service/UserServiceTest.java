package com.bb.accountbook.domain.user.service;

import com.bb.accountbook.common.model.codes.RoleCode;
import com.bb.accountbook.domain.user.repository.RoleRepository;
import com.bb.accountbook.domain.user.repository.UserRoleRepository;
import com.bb.accountbook.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleRepository roleRepository;

    @BeforeEach
    public void before() {
        roleRepository.saveAll(Arrays.stream(RoleCode.values()).map(Role::new).collect(Collectors.toList()));
    }

    @Test
    public void 회원가입() throws Exception {
        // given
        String email = "k941026h@naver.com";
        String password = "pass1";

        // when
        Long joinedUserId = userService.join(email, password);

        // then
        assertThat(joinedUserId).isEqualTo(1L);
        assertThat(userRoleRepository.findAll().size()).isEqualTo(1);
    }

}