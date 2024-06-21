package com.bb.accountbook.domain.couple.controller;

import com.bb.accountbook.common.model.ApiResponse;
import com.bb.accountbook.common.model.codes.SuccessCode;
import com.bb.accountbook.domain.couple.dto.*;
import com.bb.accountbook.domain.couple.service.CoupleService;
import com.bb.accountbook.security.SecurityContextProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.bb.accountbook.common.model.codes.SuccessCode.SUC_COUP_000;
import static com.bb.accountbook.common.model.codes.SuccessCode.SUC_COUP_001;


@RestController
@RequiredArgsConstructor
public class CoupleController {

    private final CoupleService coupleService;

    private final SecurityContextProvider securityContextProvider;

    @PostMapping("/api/v1/couple")
    public ApiResponse<CoupleConnectionResponseDto> coupleConnect(@RequestBody @Valid CoupleConnectionRequestDto requestDto) {
        Long apiCallersUserCoupleId = coupleService.connectToOpponent(securityContextProvider.getCurrentEmail(), requestDto.getOpponentEmail(), requestDto.getNickname(), requestDto.getCoupleName());
        return new ApiResponse<>(new CoupleConnectionResponseDto(apiCallersUserCoupleId), SUC_COUP_000);
    }

    @PostMapping("/api/v1/couple/apply")
    public ApiResponse<CoupleConnectionApplyResponseDto> applyCoupleRequest(@RequestBody @Valid CoupleConnectionApplyRequestDto requestDto) {
        Long apiCallersUserCoupleId = coupleService.applyConnectRequest(requestDto.getUserCoupleId(), requestDto.getNickname());
        return new ApiResponse<>(new CoupleConnectionApplyResponseDto(apiCallersUserCoupleId), SUC_COUP_001);
    }

    @GetMapping("/api/v1/couple/status")
    public ApiResponse<CoupleStatusFindResponseDto> findCoupleAndUserCoupleStatus() {
        return new ApiResponse<>(coupleService.findCoupleAndUserCoupleStatus(securityContextProvider.getCurrentEmail()));
    }

    @GetMapping("/api/v1/couple/connect")
    public ApiResponse<CoupleConnectionInfoResponseDto> findCoupleConnectionInfo() {
        return new ApiResponse<>(coupleService.findCoupleConnectionInfo(securityContextProvider.getCurrentEmail()));
    }
}
