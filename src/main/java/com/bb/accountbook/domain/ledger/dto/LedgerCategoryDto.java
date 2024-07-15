package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.entity.LedgerCategory;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LedgerCategoryDto {
    private Long ledgerCategoryId;
    private String ledgerCategoryName;
    private Long iconId;
    private String iconName;
    private LedgerCode ledgerCode;


    public LedgerCategoryDto(LedgerCategory ledgerCategory) {
        if (ledgerCategory != null) {
            this.ledgerCategoryId = ledgerCategory.getId();
            this.ledgerCategoryName = ledgerCategory.getName();
            this.iconId = ledgerCategory.getIcon().getId();
            this.iconName = ledgerCategory.getIcon().getName();
            this.ledgerCode = ledgerCategory.getLedgerCode();
        }
    }

    @QueryProjection
    public LedgerCategoryDto(Long ledgerCategoryId, String ledgerCategoryName, Long iconId, String iconName, LedgerCode ledgerCode) {
        this.ledgerCategoryId = ledgerCategoryId;
        this.ledgerCategoryName = ledgerCategoryName;
        this.iconId = iconId;
        this.iconName = iconName;
        this.ledgerCode = ledgerCode;
    }
}
