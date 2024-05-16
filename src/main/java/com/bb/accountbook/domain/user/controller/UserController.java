package com.bb.accountbook.domain.user.controller;

import com.bb.accountbook.common.model.ApiResponse;
import com.bb.accountbook.domain.user.dto.UserJoinRequestDto;
import com.bb.accountbook.domain.user.dto.UserJoinResponseDto;
import com.bb.accountbook.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/signup")
    public ApiResponse<UserJoinResponseDto> signup(@RequestBody @Valid UserJoinRequestDto userJoinRequestDto) {
        Long joinedUserId = userService.join(userJoinRequestDto.getEmail(), userJoinRequestDto.getPassword(), userJoinRequestDto.getGender());
        return new ApiResponse<>(new UserJoinResponseDto(joinedUserId));
    }

}
