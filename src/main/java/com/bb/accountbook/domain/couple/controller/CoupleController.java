package com.bb.accountbook.domain.couple.controller;

import com.bb.accountbook.common.model.OnSuccess;
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

    @OnSuccess(SUC_COUP_000)
    @PostMapping("/api/v1/couple")
    public CoupleConnectionResponseDto coupleConnect(@RequestBody @Valid CoupleConnectionRequestDto requestDto) {
        Long apiCallersUserCoupleId = coupleService.connectToOpponent(securityContextProvider.getCurrentEmail(), requestDto.getOpponentEmail(), requestDto.getNickname(), requestDto.getCoupleName());
        return new CoupleConnectionResponseDto(apiCallersUserCoupleId);
    }

    @OnSuccess(SUC_COUP_001)
    @PostMapping("/api/v1/couple/apply")
    public CoupleConnectionApplyResponseDto applyCoupleRequest(@RequestBody @Valid CoupleConnectionApplyRequestDto requestDto) {
        Long apiCallersUserCoupleId = coupleService.applyConnectRequest(requestDto.getUserCoupleId(), requestDto.getNickname());
        return new CoupleConnectionApplyResponseDto(apiCallersUserCoupleId);
    }

    @GetMapping("/api/v1/couple/status")
    public CoupleStatusFindResponseDto findCoupleAndUserCoupleStatus() {
        return coupleService.findCoupleAndUserCoupleStatus(securityContextProvider.getCurrentEmail());
    }

    @GetMapping("/api/v1/couple/connect")
    public CoupleConnectionInfoResponseDto findCoupleConnectionInfo() {
        return coupleService.findCoupleConnectionInfo(securityContextProvider.getCurrentEmail());
    }
}
