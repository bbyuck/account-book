package com.bb.accountbook.domain.user.controller;

import com.bb.accountbook.common.model.ApiResponse;
import com.bb.accountbook.common.model.codes.SuccessCode;
import com.bb.accountbook.domain.user.dto.*;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.security.SecurityContextProvider;
import com.bb.accountbook.security.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.bb.accountbook.common.model.codes.SuccessCode.*;
import static com.bb.accountbook.common.model.codes.SuccessCode.SUC_USR_000;
import static com.bb.accountbook.common.model.codes.SuccessCode.SUC_USR_001;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final TokenProvider tokenProvider;

    private final SecurityContextProvider securityContextProvider;

    @PostMapping("/api/v1/signup")
    public ApiResponse<UserSignUpResponseDto> signup(@RequestBody @Valid UserSignUpRequestDto userSignUpRequestDto) {
        Long joinedUserId = userService.signup(userSignUpRequestDto.getEmail(), userSignUpRequestDto.getPassword(), userSignUpRequestDto.getPasswordConfirm());
        return new ApiResponse<>(new UserSignUpResponseDto(joinedUserId), SUC_USR_000);
    }

    @PostMapping("/api/v2/signup")
    public ApiResponse<UserSignUpResponseDto> signupWithEmailVerification(@RequestBody @Valid UserSignUpRequestDto userSignUpRequestDto) {
        Long joinedUserId = userService.signupWithEmailVerification(userSignUpRequestDto.getEmail(), userSignUpRequestDto.getPassword(), userSignUpRequestDto.getPasswordConfirm());
        return new ApiResponse<>(new UserSignUpResponseDto(joinedUserId), SUC_USR_001);
    }

    @PostMapping("/api/v1/authenticate")
    public ApiResponse<TokenDto> login(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response) {
        TokenDto tokenDto = userService.authenticate(loginDto.getEmail(), loginDto.getPassword());
        userService.updateAuth(securityContextProvider.getCurrentEmail(), tokenDto, loginDto.isAutoLogin());
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());

        return new ApiResponse<>(tokenDto);
    }

    @PostMapping("/api/v1/reissue/token")
    public ApiResponse<TokenDto> reissueToken(HttpServletRequest request) {
        String refreshToken = tokenProvider.resolveRefreshToken(request);
        return new ApiResponse<>(userService.reissueToken(refreshToken));
    }

    @PostMapping("/api/v1/logout")
    public ApiResponse<LogoutResponseDto> logout() {
        return new ApiResponse<>(new LogoutResponseDto(userService.logout(securityContextProvider.getCurrentEmail())), SUC_USR_002);
    }

    @PostMapping("/api/v1/verify")
    public ApiResponse<UserVerifyResponseDto> verifyUser(@RequestBody @Valid UserVerifyRequestDto requestDto) {
        return new ApiResponse<>(new UserVerifyResponseDto(userService.verifyUser(requestDto.getTarget())), USC_USR_004);
    }

    @PutMapping("/api/v1/user/password")
    public ApiResponse<UserPasswordChangeResponseDto> changeUserPassword(@RequestBody @Valid UserPasswordChangeRequestDto requestDto) {
        return new ApiResponse<>(new UserPasswordChangeResponseDto(
                userService.changeUserPassword(
                        securityContextProvider.getCurrentEmail()
                        , requestDto.getPassword()
                        , requestDto.getNewPassword()
                        , requestDto.getNewPasswordConfirm())), SUC_USR_003);
    }

}
