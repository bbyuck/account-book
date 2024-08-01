package com.bb.accountbook.domain.ledger.controller;

import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.common.validation.presentation.constraints.YearMonth;
import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerCategoryStatistic;
import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerRequestDto;
import com.bb.accountbook.domain.ledger.dto.PeriodLedgerCodeStatistic;
import com.bb.accountbook.domain.ledger.dto.PeriodLedgerStatisticRequestDto;
import com.bb.accountbook.domain.ledger.service.LedgerStatisticService;
import com.bb.accountbook.security.SecurityContextProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LedgerStatisticController {

    private final LedgerStatisticService ledgerStatisticService;

    private final SecurityContextProvider securityContextProvider;

    @GetMapping("/api/v1/ledger/statistic/monthly/categorization")
    public MonthlyLedgerCategoryStatistic findMonthlyCategorizationStatistic(@RequestParam("ym") @Valid @YearMonth String yearMonth
            , @RequestParam(value = "ledgerCode", required = false) LedgerCode ledgerCode) {
        return ledgerStatisticService.findCategoryStatisticPerLedgerCode(MonthlyLedgerRequestDto
                .builder()
                .email(securityContextProvider.getCurrentEmail())
                .yearMonth(yearMonth)
                .ledgerCode(ledgerCode)
                .build());
    }

    @GetMapping("/api/v1/ledger/statistic/period/categorization")
    public PeriodLedgerCodeStatistic findPeriodCategorizationStatistic(PeriodLedgerStatisticRequestDto requestDto) {
        requestDto.setEmail(securityContextProvider.getCurrentEmail());
        return ledgerStatisticService.findPeriodStatisticPerLedgerCode(requestDto);
    }

}
