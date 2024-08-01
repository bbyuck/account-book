package com.bb.accountbook.domain.user.controller;

import com.bb.accountbook.common.model.OnSuccess;
import com.bb.accountbook.domain.user.dto.*;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.security.SecurityContextProvider;
import com.bb.accountbook.security.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.bb.accountbook.common.model.codes.SuccessCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final TokenProvider tokenProvider;

    private final SecurityContextProvider securityContextProvider;

    @OnSuccess(SUC_USR_000)
    @PostMapping("/api/v1/signup")
    public UserSignUpResponseDto signup(@RequestBody @Valid UserSignUpRequestDto userSignUpRequestDto) {
        Long joinedUserId = userService.signup(userSignUpRequestDto.getEmail(), userSignUpRequestDto.getPassword(), userSignUpRequestDto.getPasswordConfirm());
        return new UserSignUpResponseDto(joinedUserId);
    }

    @OnSuccess(SUC_USR_001)
    @PostMapping("/api/v2/signup")
    public UserSignUpResponseDto signupWithEmailVerification(@RequestBody @Valid UserSignUpRequestDto userSignUpRequestDto) {
        Long joinedUserId = userService.signupWithEmailVerification(userSignUpRequestDto.getEmail(), userSignUpRequestDto.getPassword(), userSignUpRequestDto.getPasswordConfirm());
        return new UserSignUpResponseDto(joinedUserId);
    }

    @PostMapping("/api/v1/authenticate")
    public TokenDto login(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response) {
        TokenDto tokenDto = userService.authenticate(loginDto.getEmail(), loginDto.getPassword());
        userService.updateAuth(securityContextProvider.getCurrentEmail(), tokenDto, loginDto.isAutoLogin());
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());

        return tokenDto;
    }

    @PostMapping("/api/v1/reissue/token")
    public TokenDto reissueToken(HttpServletRequest request) {
        String refreshToken = tokenProvider.resolveRefreshToken(request);
        return userService.reissueToken(refreshToken);
    }

    @OnSuccess(SUC_USR_002)
    @PostMapping("/api/v1/logout")
    public LogoutResponseDto logout() {
        return new LogoutResponseDto(userService.logout(securityContextProvider.getCurrentEmail()));
    }

    @OnSuccess(SUC_USR_004)
    @PostMapping("/api/v1/verify")
    public UserVerifyResponseDto verifyUser(@RequestBody @Valid UserVerifyRequestDto requestDto) {
        return new UserVerifyResponseDto(userService.verifyUser(requestDto.getTarget()));
    }

    @OnSuccess(SUC_USR_003)
    @PutMapping("/api/v1/user/password")
    public UserPasswordChangeResponseDto changeUserPassword(@RequestBody @Valid UserPasswordChangeRequestDto requestDto) {
        return new UserPasswordChangeResponseDto(
                userService.changeUserPassword(
                        securityContextProvider.getCurrentEmail()
                        , requestDto.getPassword()
                        , requestDto.getNewPassword()
                        , requestDto.getNewPasswordConfirm()));
    }

}
