package com.bb.accountbook.domain.ledger.controller;


import com.bb.accountbook.common.model.ApiResponse;
import com.bb.accountbook.domain.ledger.dto.LedgerCategoryCreateRequestDto;
import com.bb.accountbook.domain.ledger.dto.LedgerCategoryCreateResponseDto;
import com.bb.accountbook.domain.ledger.service.LedgerCategoryService;
import com.bb.accountbook.security.SecurityContextProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LedgerCategoryController {
    private final LedgerCategoryService ledgerCategoryService;

    private final SecurityContextProvider securityContextProvider;

    @PostMapping("/api/v1/ledger/category")
    public ApiResponse<LedgerCategoryCreateResponseDto> createLedgerCategory(@RequestBody LedgerCategoryCreateRequestDto requestDto) {
        return new ApiResponse<>(
                new LedgerCategoryCreateResponseDto(
                        ledgerCategoryService.insertLedgerCategory(
                                securityContextProvider.getCurrentEmail(),
                                requestDto.getLedgerCategoryName(),
                                requestDto.getLedgerCode())
                )
        );
    }

}
