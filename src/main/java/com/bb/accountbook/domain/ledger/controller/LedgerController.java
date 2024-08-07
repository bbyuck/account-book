package com.bb.accountbook.domain.ledger.controller;

import com.bb.accountbook.common.model.OnSuccess;
import com.bb.accountbook.common.validation.presentation.constraints.YearMonth;
import com.bb.accountbook.domain.ledger.dto.*;
import com.bb.accountbook.domain.ledger.service.LedgerPresentationService;
import com.bb.accountbook.domain.ledger.service.LedgerService;
import com.bb.accountbook.entity.Ledger;
import com.bb.accountbook.security.SecurityContextProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bb.accountbook.common.model.codes.SuccessCode.*;


@Slf4j
@RestController
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerService ledgerService;

    private final LedgerPresentationService ledgerPresentationService;

    private final SecurityContextProvider securityContextProvider;

    @OnSuccess(SUC_LED_000)
    @PostMapping("/api/v1/ledger")
    public LedgerInsertResponseDto insertLedger(@RequestBody @Valid LedgerInsertRequestDto requestDto) {
        return new LedgerInsertResponseDto(
                ledgerService.insertLedger(requestDto.getLedgerCategoryId(),
                        securityContextProvider.getCurrentEmail(), requestDto.getLedgerCode(),
                        requestDto.getLedgerDate(),
                        requestDto.getLedgerAmount(),
                        requestDto.getLedgerDescription()));
    }

    @OnSuccess(SUC_LED_001)
    @PutMapping("/api/v1/ledger/{ledgerId}")
    public LedgerUpdateResponseDto updateLedger(@PathVariable("ledgerId") Long ledgerId, @RequestBody @Valid LedgerUpdateRequestDto requestDto) {
        return new LedgerUpdateResponseDto(
                ledgerService.updateLedger(
                        securityContextProvider.getCurrentEmail(),
                        ledgerId,
                        requestDto.getLedgerCode(),
                        requestDto.getLedgerDate(),
                        requestDto.getLedgerAmount(),
                        requestDto.getLedgerDescription(),
                        requestDto.getLedgerCategoryId()));
    }

    @GetMapping("/api/v1/ledger/{ledgerId}")
    public LedgerDto findLedger(@PathVariable("ledgerId") Long ledgerId) {
        return new LedgerDto(ledgerService.findLedger(securityContextProvider.getCurrentEmail(), ledgerId));
    }

    @OnSuccess(SUC_LED_002)
    @DeleteMapping("/api/v1/ledger/{ledgerId}")
    public LedgerDeleteResponseDto deleteLedger(@PathVariable("ledgerId") Long ledgerId) {
        return new LedgerDeleteResponseDto(ledgerService.deleteLedger(securityContextProvider.getCurrentEmail(), ledgerId));
    }

    @GetMapping("/api/v1/personal/ledger/{ledgerId}")
    public LedgerDto findPersonalLedger(@PathVariable("ledgerId") Long ledgerId) {
        return new LedgerDto(ledgerService.findPersonalLedger(securityContextProvider.getCurrentEmail(), ledgerId));
    }

    @GetMapping("/api/v1/couple/ledger/{ledgerId}")
    public LedgerDto findCoupleLedger(@PathVariable("ledgerId") Long ledgerId, @RequestParam("ci") Long coupleId) {
        return new LedgerDto(ledgerService.findCoupleLedger(coupleId, ledgerId));
    }

    @GetMapping("/api/v1/monthly/ledger")
    public MonthlyLedgerResponseDto findMonthlyLedger(@RequestParam("ym") @Valid @YearMonth String yearMonth) {
        List<Ledger> monthlyLedger = ledgerService.findMonthlyLedger(
                MonthlyLedgerRequestDto.builder()
                        .email(securityContextProvider.getCurrentEmail())
                        .yearMonth(yearMonth)
                        .build());
        return ledgerPresentationService.getMonthlyLedgerResponseDto(monthlyLedger, yearMonth);
    }

    @GetMapping("/api/v1/monthly/couple/ledger")
    public MonthlyLedgerResponseDto findCoupleMonthlyLedger(@RequestParam("ym") @Valid @YearMonth String yearMonth) {
        List<Ledger> monthlyLedgers = ledgerService.findCoupleMonthlyLedger(
                MonthlyLedgerRequestDto.builder()
                        .email(securityContextProvider.getCurrentEmail())
                        .yearMonth(yearMonth)
                        .build()
        );
        return ledgerPresentationService.getMonthlyLedgerResponseDto(monthlyLedgers, yearMonth);
    }

    @GetMapping("/api/v1/monthly/personal/ledger")
    public MonthlyLedgerResponseDto findPersonalMonthlyLedger(@RequestParam("ym") @Valid @YearMonth String yearMonth) {
        List<Ledger> monthlyLedgers = ledgerService.findPersonalMonthlyLedger(
                MonthlyLedgerRequestDto.builder()
                        .email(securityContextProvider.getCurrentEmail())
                        .yearMonth(yearMonth)
                        .build()
        );
        return ledgerPresentationService.getMonthlyLedgerResponseDto(monthlyLedgers, yearMonth);
    }

    @GetMapping("/api/v1/personal/asset")
    public AssetDto findPersonalAsset() {
        return ledgerService.findPersonalAsset(securityContextProvider.getCurrentEmail());
    }

    @GetMapping("/api/v1/couple/asset")
    public AssetDto findCoupleAsset() {
        return ledgerService.findCoupleAsset(securityContextProvider.getCurrentEmail());
    }

}
