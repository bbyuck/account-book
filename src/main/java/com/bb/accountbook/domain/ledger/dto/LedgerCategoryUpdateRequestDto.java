package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import lombok.Data;

@Data
public class LedgerCategoryUpdateRequestDto {
    private String ledgerCategoryName;
    private LedgerCode ledgerCode;
    private Long iconId;
}
