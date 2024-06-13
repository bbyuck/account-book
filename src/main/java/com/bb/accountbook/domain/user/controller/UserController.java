package com.bb.accountbook.domain.user.controller;

import com.bb.accountbook.common.model.ApiResponse;
import com.bb.accountbook.domain.user.dto.*;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.security.SecurityContextProvider;
import com.bb.accountbook.security.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        return new ApiResponse<>(new UserSignUpResponseDto(joinedUserId), "가입이 완료되었습니다.");
    }

    @PostMapping("/api/v1/authenticate")
    public ApiResponse<TokenDto> login(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response) {
        TokenDto tokenDto = userService.authenticate(loginDto.getEmail(), loginDto.getPassword(), loginDto.isAutoLogin());
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
        return new ApiResponse<>(new LogoutResponseDto(userService.logout(securityContextProvider.getCurrentEmail())));
    }
}
