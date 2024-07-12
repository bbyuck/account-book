package com.bb.accountbook.domain.ledger.service;

import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerResponseDto;
import com.bb.accountbook.entity.Ledger;

import java.util.List;


/**
 * Presentation Layer에서 API Spec을 맞추기 위해 DTO 정제를 위한 Service
 */
public interface LedgerPresentationService {

    /**
     * 월별 가계부 조회 API Response 정제
     * @param monthlyLedgers
     * @param yearMonth
     * @return
     */
    MonthlyLedgerResponseDto getMonthlyLedgerResponseDto(List<Ledger> monthlyLedgers, String yearMonth);
}
