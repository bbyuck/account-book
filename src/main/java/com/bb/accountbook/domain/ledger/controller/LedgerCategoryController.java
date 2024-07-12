package com.bb.accountbook.domain.ledger.controller;


import com.bb.accountbook.common.model.ApiResponse;
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

    @PostMapping("/api/v1/ledger/category")
    public ApiResponse<LedgerCategoryCreateResponseDto> createLedgerCategory(@RequestBody LedgerCategoryCreateRequestDto requestDto) {
        return new ApiResponse<>(
                new LedgerCategoryCreateResponseDto(
                        ledgerCategoryService.insertLedgerCategory(
                                securityContextProvider.getCurrentEmail(),
                                requestDto.getLedgerCategoryName(),
                                requestDto.getLedgerCode(),
                                requestDto.getIconId())
                ), SUC_LED_003
        );
    }

    @GetMapping("/api/v1/ledger/category")
    public ApiResponse<LedgerCategoryListDto> findOwnLedgerCategories() {
        return new ApiResponse<>(new LedgerCategoryListDto(
                ledgerCategoryService.findOwnLedgerCategories(securityContextProvider.getCurrentEmail())
                        .stream()
                        .map(LedgerCategoryDto::new)
                        .collect(Collectors.toList())
        ));
    }

    @GetMapping("/api/v1/ledger/category/{categoryId}")
    public ApiResponse<LedgerCategoryDto> findOwnLedgerCategory(@PathVariable("categoryId") Long categoryId) {
        return new ApiResponse<>(new LedgerCategoryDto(ledgerCategoryService.findOwnLedgerCategory(securityContextProvider.getCurrentEmail(), categoryId)));
    }

    @PutMapping("/api/v1/ledger/category/{categoryId}")
    public ApiResponse<LedgerCategoryUpdateResponseDto> updateOwnLedgerCategory(@PathVariable("categoryId") Long categoryId, @RequestBody LedgerCategoryUpdateRequestDto requestDto) {
        ledgerCategoryService.updateOwnLedgerCategory(securityContextProvider.getCurrentEmail(), categoryId, requestDto.getLedgerCategoryName(), requestDto.getLedgerCode(), requestDto.getIconId());
        return new ApiResponse<>(new LedgerCategoryUpdateResponseDto(), SUC_LED_004);
    }

    @DeleteMapping("/api/v1/ledger/category/{categoryId}")
    public ApiResponse<LedgerCategoryDeleteResponseDto> deleteOwnLedgerCategory(@PathVariable("categoryId") Long categoryId) {
        ledgerCategoryService.deleteOwnLedgerCategory(securityContextProvider.getCurrentEmail(), categoryId);
        return new ApiResponse<>(new LedgerCategoryDeleteResponseDto(), SUC_LED_005);
    }
}
