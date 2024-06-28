package com.bb.accountbook.domain.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LedgerCategoryListDto {
    private List<LedgerCategoryDto> ledgerCategoryList;
}
