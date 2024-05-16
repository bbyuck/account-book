package com.bb.accountbook.domain.couple.controller;

import com.bb.accountbook.common.model.ApiResponse;
import com.bb.accountbook.domain.couple.dto.CoupleConnectionApplyRequestDto;
import com.bb.accountbook.domain.couple.dto.CoupleConnectionApplyResponseDto;
import com.bb.accountbook.domain.couple.dto.CoupleConnectionRequestDto;
import com.bb.accountbook.domain.couple.dto.CoupleConnectionResponseDto;
import com.bb.accountbook.domain.couple.service.CoupleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.bb.accountbook.security.SecurityContextProvider.getCurrentUserId;

@RestController
@RequiredArgsConstructor
public class CoupleController {

    private final CoupleService coupleService;

    @PostMapping("/api/v1/couple")
    public ApiResponse<CoupleConnectionResponseDto> coupleConnect(@RequestBody @Valid CoupleConnectionRequestDto requestDto) {
        Long apiCallersUserCoupleId = coupleService.connectToOpponent(getCurrentUserId(), requestDto.getOpponentEmail(), requestDto.getNickname(), requestDto.getCoupleName());
        return new ApiResponse<>(new CoupleConnectionResponseDto(apiCallersUserCoupleId));
    }

    @PostMapping("/api/v1/couple/apply")
    public ApiResponse<CoupleConnectionApplyResponseDto> applyCoupleRequest(@RequestBody @Valid CoupleConnectionApplyRequestDto requestDto) {
        Long apiCallersUserCoupleId = coupleService.applyConnectRequest(requestDto.getUserCoupleId(), requestDto.getNickname());
        return new ApiResponse<>(new CoupleConnectionApplyResponseDto(apiCallersUserCoupleId));
    }
}
