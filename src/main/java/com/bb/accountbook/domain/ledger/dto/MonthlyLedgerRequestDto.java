package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MonthlyLedgerRequestDto {
    private String email;
    private LedgerCode ledgerCode;
    private String yearMonth;
}
