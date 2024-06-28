package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.entity.LedgerCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LedgerCategoryDto {
    private Long ledgerCategoryId;
    private String ledgerCategoryName;
    private String iconSrcPath;
    private LedgerCode ledgerCode;

    public LedgerCategoryDto(LedgerCategory ledgerCategory) {
        this.ledgerCategoryId = ledgerCategory.getId();
        this.ledgerCategoryName = ledgerCategory.getName();
        this.iconSrcPath = ledgerCategory.getIcon().getSrcPath();
        this.ledgerCode = ledgerCategory.getLedgerCode();
    }
}
