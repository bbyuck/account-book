package com.bb.accountbook.domain.ledger.controller;

import com.bb.accountbook.common.model.ApiResponse;
import com.bb.accountbook.common.model.codes.SuccessCode;
import com.bb.accountbook.common.validation.presentation.constraints.YearMonth;
import com.bb.accountbook.domain.ledger.dto.*;
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

    private final SecurityContextProvider securityContextProvider;

    @PostMapping("/api/v1/ledger")
    public ApiResponse<LedgerInsertResponseDto> insertLedger(@RequestBody @Valid LedgerInsertRequestDto requestDto) {
        return new ApiResponse<>(new LedgerInsertResponseDto(
                ledgerService.insertLedger(requestDto.getLedgerCategoryId(),
                        securityContextProvider.getCurrentEmail(), requestDto.getLedgerCode(),
                        requestDto.getLedgerDate(),
                        requestDto.getLedgerAmount(),
                        requestDto.getLedgerDescription())), SUC_LED_000);
    }

    @PutMapping("/api/v1/ledger/{ledgerId}")
    public ApiResponse<LedgerUpdateResponseDto> updateLedger(@PathVariable("ledgerId") Long ledgerId, @RequestBody @Valid LedgerUpdateRequestDto requestDto) {
        return new ApiResponse<>(new LedgerUpdateResponseDto(
                ledgerService.updateLedger(
                        securityContextProvider.getCurrentEmail(),
                        ledgerId,
                        requestDto.getLedgerCode(),
                        requestDto.getLedgerDate(),
                        requestDto.getLedgerAmount(),
                        requestDto.getLedgerDescription(),
                        requestDto.getLedgerCategoryId())), SUC_LED_001);
    }

    @GetMapping("/api/v1/ledger/{ledgerId}")
    public ApiResponse<LedgerDto> findLedger(@PathVariable("ledgerId") Long ledgerId) {
        return new ApiResponse<>(new LedgerDto(ledgerService.findLedger(securityContextProvider.getCurrentEmail(), ledgerId)));
    }

    @DeleteMapping("/api/v1/ledger/{ledgerId}")
    public ApiResponse<LedgerDeleteResponseDto> deleteLedger(@PathVariable("ledgerId") Long ledgerId) {
        return new ApiResponse<>(new LedgerDeleteResponseDto(ledgerService.deleteLedger(securityContextProvider.getCurrentEmail(), ledgerId)), SUC_LED_002);
    }

    @GetMapping("/api/v1/personal/ledger/{ledgerId}")
    public ApiResponse<LedgerDto> findPersonalLedger(@PathVariable("ledgerId") Long ledgerId) {
        return new ApiResponse<>(new LedgerDto(ledgerService.findPersonalLedger(securityContextProvider.getCurrentEmail(), ledgerId)));
    }

    @GetMapping("/api/v1/couple/ledger/{ledgerId}")
    public ApiResponse<LedgerDto> findCoupleLedger(@PathVariable("ledgerId") Long ledgerId, @RequestParam("ci") Long coupleId) {
        return new ApiResponse<>(new LedgerDto(ledgerService.findCoupleLedger(coupleId, ledgerId)));
    }

    @GetMapping("/api/v1/monthly/ledger")
    public ApiResponse<MonthlyLedgerResponseDto> findMonthlyLedger(@RequestParam("ym") @Valid @YearMonth String yearMonth) {
        List<Ledger> monthlyLedger = ledgerService.findMonthlyLedger(securityContextProvider.getCurrentEmail(), yearMonth);
        return new ApiResponse<>(ledgerService.getMonthlyLedgerResponseDto(monthlyLedger, yearMonth));
    }

    @GetMapping("/api/v1/monthly/couple/ledger")
    public ApiResponse<MonthlyLedgerResponseDto> findCoupleMonthlyLedger(@RequestParam("ym") @Valid @YearMonth String yearMonth) {
        List<Ledger> monthlyLedgers = ledgerService.findCoupleMonthlyLedger(securityContextProvider.getCurrentEmail(), yearMonth);
        return new ApiResponse<>(ledgerService.getMonthlyLedgerResponseDto(monthlyLedgers, yearMonth));
    }

    @GetMapping("/api/v1/monthly/personal/ledger")
    public ApiResponse<MonthlyLedgerResponseDto> findPersonalMonthlyLedger(@RequestParam("ym") @Valid @YearMonth String yearMonth) {
        List<Ledger> monthlyLedgers = ledgerService.findPersonalMonthlyLedger(securityContextProvider.getCurrentEmail(), yearMonth);
        return new ApiResponse<>(ledgerService.getMonthlyLedgerResponseDto(monthlyLedgers, yearMonth));
    }

    @GetMapping("/api/v1/personal/asset")
    public ApiResponse<AssetDto> findPersonalAsset() {
        return new ApiResponse<>(ledgerService.findPersonalAsset(securityContextProvider.getCurrentEmail()));
    }

    @GetMapping("/api/v1/couple/asset")
    public ApiResponse<AssetDto> findCoupleAsset() {
        return new ApiResponse<>(ledgerService.findCoupleAsset(securityContextProvider.getCurrentEmail()));
    }
}
