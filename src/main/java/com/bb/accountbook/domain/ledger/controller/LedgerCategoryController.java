package com.bb.accountbook.domain.ledger.controller;


import com.bb.accountbook.common.model.OnSuccess;
import com.bb.accountbook.domain.ledger.dto.*;
import com.bb.accountbook.domain.ledger.service.impl.LedgerCategoryServiceImpl;
import com.bb.accountbook.security.SecurityContextProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.bb.accountbook.common.model.codes.SuccessCode.*;

@RestController
@RequiredArgsConstructor
public class LedgerCategoryController {
    private final LedgerCategoryServiceImpl ledgerCategoryService;

    private final SecurityContextProvider securityContextProvider;

    @OnSuccess(SUC_LED_003)
    @PostMapping("/api/v1/ledger/category")
    public LedgerCategoryCreateResponseDto createLedgerCategory(@RequestBody LedgerCategoryCreateRequestDto requestDto) {
        return new LedgerCategoryCreateResponseDto(
                ledgerCategoryService.insertLedgerCategory(
                        securityContextProvider.getCurrentEmail(),
                        requestDto.getLedgerCategoryName(),
                        requestDto.getLedgerCode(),
                        requestDto.getIconId()));
    }

    @GetMapping("/api/v1/ledger/category")
    public LedgerCategoryListDto findOwnLedgerCategories() {
        return new LedgerCategoryListDto(
                ledgerCategoryService.findOwnLedgerCategories(securityContextProvider.getCurrentEmail())
                        .stream()
                        .map(LedgerCategoryDto::new)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/api/v1/ledger/category/{categoryId}")
    public LedgerCategoryDto findOwnLedgerCategory(@PathVariable("categoryId") Long categoryId) {
        return new LedgerCategoryDto(ledgerCategoryService.findOwnLedgerCategory(securityContextProvider.getCurrentEmail(), categoryId));
    }

    @OnSuccess(SUC_LED_004)
    @PutMapping("/api/v1/ledger/category/{categoryId}")
    public LedgerCategoryUpdateResponseDto updateOwnLedgerCategory(@PathVariable("categoryId") Long categoryId, @RequestBody LedgerCategoryUpdateRequestDto requestDto) {
        ledgerCategoryService.updateOwnLedgerCategory(securityContextProvider.getCurrentEmail(), categoryId, requestDto.getLedgerCategoryName(), requestDto.getLedgerCode(), requestDto.getIconId());
        return new LedgerCategoryUpdateResponseDto();
    }

    @OnSuccess(SUC_LED_005)
    @DeleteMapping("/api/v1/ledger/category/{categoryId}")
    public LedgerCategoryDeleteResponseDto deleteOwnLedgerCategory(@PathVariable("categoryId") Long categoryId) {
        ledgerCategoryService.deleteOwnLedgerCategory(securityContextProvider.getCurrentEmail(), categoryId);
        return new LedgerCategoryDeleteResponseDto();
    }
}
