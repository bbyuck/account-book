package com.bb.accountbook.domain.group.controller;

import com.bb.accountbook.common.model.ApiResponse;
import com.bb.accountbook.domain.group.dto.ConnectRequestDto;
import com.bb.accountbook.domain.group.dto.ConnectResponseDto;
import com.bb.accountbook.domain.group.service.GroupService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    private final HttpServletRequest request;


    @PostMapping("/api/v1/connect")
    public ApiResponse<ConnectResponseDto> connectToOpponent(@RequestBody @Validated ConnectRequestDto connectRequestDto) {
        return new ApiResponse<>(
                new ConnectResponseDto(
                        groupService.connectToOpponent(
                                Long.valueOf(request.getHeader("userId")),
                                connectRequestDto.getOpponentEmail(),
                                connectRequestDto.getNickname(),
                                connectRequestDto.getMemberCode()
                        )
                ));
    }

}
