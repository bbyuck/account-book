package com.bb.accountbook.domain.user.controller;

import com.bb.accountbook.common.model.ApiResponse;
import com.bb.accountbook.domain.user.dto.AdminLoginResponseDto;
import com.bb.accountbook.domain.user.dto.LoginDto;
import com.bb.accountbook.domain.user.dto.TokenDto;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.security.SecurityContextProvider;
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
public class UserAdminController {

    private final UserService userService;

    private final SecurityContextProvider securityContextProvider;

    @PostMapping("/api/admin/v1/authenticate")
    public ApiResponse<AdminLoginResponseDto> loginForAdmin(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response) {
        TokenDto tokenDto = userService.authenticateAdmin(loginDto.getEmail(), loginDto.getPassword());
        userService.updateAuth(securityContextProvider.getCurrentEmail(), tokenDto, loginDto.isAutoLogin());
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());

        return new ApiResponse<>(new AdminLoginResponseDto(tokenDto, loginDto.getEmail()));
    }
}
