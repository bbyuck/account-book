package com.bb.accountbook.domain.ledger.controller;

import com.bb.accountbook.common.model.ApiResponse;
import com.bb.accountbook.common.model.HttpServletRequestContext;
import com.bb.accountbook.domain.ledger.dto.LedgerInsertRequestDto;
import com.bb.accountbook.domain.ledger.dto.LedgerInsertResponseDto;
import com.bb.accountbook.domain.ledger.dto.LedgerUpdateRequestDto;
import com.bb.accountbook.domain.ledger.dto.LedgerUpdateResponseDto;
import com.bb.accountbook.domain.ledger.service.LedgerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerService ledgerService;

    private final HttpServletRequestContext context;

    @PostMapping("/api/v1/ledger")
    public ApiResponse<LedgerInsertResponseDto> insertLedger(@RequestBody @Valid LedgerInsertRequestDto requestDto) {
        Long savedLedgerId = ledgerService.insertLedger(context.getUserId(), requestDto);
        return new ApiResponse<>(new LedgerInsertResponseDto(savedLedgerId));
    }

    @PutMapping("/api/v1/ledger/{ledgerId}")
    public ApiResponse<LedgerUpdateResponseDto> updateLedger(@PathVariable Long ledgerId, @RequestBody @Valid LedgerUpdateRequestDto requestDto) {
        Long updatedLedgerId = ledgerService.updateLedger(ledgerId, requestDto);
        return new ApiResponse<>(new LedgerUpdateResponseDto(updatedLedgerId));
    }
}
