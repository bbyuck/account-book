package com.bb.accountbook.domain.user.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.domain.user.dto.TokenDto;
import com.bb.accountbook.domain.user.repository.AuthRepository;
import com.bb.accountbook.domain.user.repository.RoleRepository;
import com.bb.accountbook.entity.Auth;
import com.bb.accountbook.entity.User;
import org.assertj.core.api.Assertions;
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

    @Autowired
    AuthRepository authRepository;


    @Test
    @DisplayName("회원가입 - 성공")
    public void join1() throws Exception {
        // given
        String email = "wmfap1234@naver.com";
        String password = "1q2w3e4R!@";
        String rightPasswordConfirm = "1q2w3e4R!@";

        // when
        Long joinedUserId = userService.signup(email, password, rightPasswordConfirm);
        User joinedUser = userService.findUserById(joinedUserId);

        // then
        assertThat(joinedUser.getEmail()).isEqualTo(email);
        assertThat(passwordEncoder.matches(password, joinedUser.getPassword())).isTrue();
    }

    @Test
    @DisplayName("패스워드 변경")
    public void change_password() throws Exception {
        // given
        String email = "wmfap1234@naver.com";
        String password = "1q2w3e4R!@";
        String rightPasswordConfirm = "1q2w3e4R!@";

        // when
        Long joinedUserId = userService.signup(email, password, rightPasswordConfirm);
        User joinedUser = userService.findUserById(joinedUserId);

        // login
        TokenDto tokenDto = userService.authenticate(joinedUser.getEmail(), password);
        userService.updateAuth(joinedUser.getEmail(), tokenDto, true);

        // change password
        String newPassword = "wpassword123!@#";
        userService.changeUserPassword(joinedUser.getEmail(), password, newPassword, newPassword);

        Auth auth = authRepository.findByUserEmail(joinedUser.getEmail()).orElseThrow(() -> new IllegalStateException("auth 찾을 수 없음"));

        // then

        // logout 확인
        Assertions.assertThat(auth.getRefreshToken()).isNull();
        Assertions.assertThat(auth.isAutoLogin()).isFalse();

        TokenDto token = userService.authenticate(email, newPassword);
        Assertions.assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("회원가입 - 잘못된 비밀번호 입력")
    public void join_wrong_password() throws Exception {
        // given
        String email = "joinTest1@naver.com";
        String password = "pass1";
        String wrongPasswordConfirm = "pass1";

        // when
        assertThrows(GlobalException.class, () -> userService.signup(email, password, wrongPasswordConfirm));
    }

    @Test
    @DisplayName("회원가입 - 잘못된 비밀번호 확인 입력")
    public void join_wrong_password_confirm() throws Exception {
        // given
        String email = "joinTest1@naver.com";
        String password = "1q2w3e4R!@";
        String wrongPasswordConfirm = "wrong";

        // when
        assertThrows(GlobalException.class, () -> userService.signup(email, password, wrongPasswordConfirm));
    }

    @Test
    @DisplayName("중복 가입 방지")
    public void join2() throws Exception {
        // given
        String email = "k941026h@naver.com";
        String password = "1q2w3e4R!@";

        // when
        assertThrows(GlobalException.class, () -> userService.signup(email, password, password), ErrorCode.ERR_USR_001.getValue());

        // then
    }
}