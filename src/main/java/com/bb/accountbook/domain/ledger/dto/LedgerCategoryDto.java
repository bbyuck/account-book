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
    private Long iconId;
    private String iconName;
    private LedgerCode ledgerCode;

    public LedgerCategoryDto(LedgerCategory ledgerCategory) {
        this.ledgerCategoryId = ledgerCategory.getId();
        this.ledgerCategoryName = ledgerCategory.getName();
        this.iconId = ledgerCategory.getIcon().getId();
        this.iconName = ledgerCategory.getIcon().getName();
        this.ledgerCode = ledgerCategory.getLedgerCode();
    }
}
