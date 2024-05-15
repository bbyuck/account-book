package com.bb.accountbook.domain.ledger.controller;

import com.bb.accountbook.common.model.ApiResponse;
import com.bb.accountbook.common.model.HttpServletRequestContext;
import com.bb.accountbook.domain.couple.service.CoupleService;
import com.bb.accountbook.domain.ledger.dto.*;
import com.bb.accountbook.domain.ledger.service.LedgerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerService ledgerService;

    private final CoupleService coupleService;

    private final HttpServletRequestContext context;

    @PostMapping("/api/v1/ledger")
    public ApiResponse<LedgerInsertResponseDto> insertLedger(@RequestBody @Valid LedgerInsertRequestDto requestDto) {
        Long savedLedgerId = ledgerService.insertLedger(context.getUserId(), requestDto.getLedgerCode(), requestDto.getLedgerDate(), requestDto.getLedgerAmount(), requestDto.getLedgerDescription());
        return new ApiResponse<>(new LedgerInsertResponseDto(savedLedgerId));
    }

    @PutMapping("/api/v1/ledger/{ledgerId}")
    public ApiResponse<LedgerUpdateResponseDto> updateLedger(@PathVariable("ledgerId") Long ledgerId, @RequestBody @Valid LedgerUpdateRequestDto requestDto) {
        Long updatedLedgerId = ledgerService.updateLedger(ledgerId, requestDto.getLedgerCode(), requestDto.getLedgerDate(), requestDto.getLedgerAmount(), requestDto.getLedgerDescription());
        return new ApiResponse<>(new LedgerUpdateResponseDto(updatedLedgerId));
    }

    @GetMapping("/api/v1/couple/monthly/ledger")
    public ApiResponse<MonthlyLedgerResponseDto> findCoupleMonthlyLedger(@Param("ym") String yearMonth) {
        List<MonthlyLedgerDto> monthlyLedgers = ledgerService.findCoupleMonthlyLedger(context.getUserId(), yearMonth).stream()
                .map(ledger -> new CoupleMonthlyLedgerDto(
                        ledger.getOwner().getUserCouple().getNickname(),
                        ledger.getCode().getValue(),
                        ledger.getDate(),
                        ledger.getAmount(),
                        ledger.getDescription()))
                .collect(Collectors.toList());

        return new ApiResponse<>(new MonthlyLedgerResponseDto(yearMonth, monthlyLedgers));
    }

    @GetMapping("/api/v1/personal/monthly/ledger")
    public ApiResponse<MonthlyLedgerResponseDto> findPersonalMonthlyLedger(@Param("ym") String yearMonth) {
        List<MonthlyLedgerDto> monthlyLedgers = ledgerService.findPersonalMonthlyLedger(context.getUserId(), yearMonth)
                .stream()
                .map(ledger -> new PersonalMonthlyLedgerDto(
                        ledger.getCode().getValue(),
                        ledger.getDate(),
                        ledger.getAmount(),
                        ledger.getDescription()))
                .collect(Collectors.toList());

        return new ApiResponse<>(new MonthlyLedgerResponseDto(yearMonth, monthlyLedgers));
    }

}
