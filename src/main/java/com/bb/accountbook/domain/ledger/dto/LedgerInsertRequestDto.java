package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LedgerInsertRequestDto {

    @NotNull
    private LocalDate ledgerDate;

    @NotNull
    private LedgerCode ledgerCode;

    @NotNull
    private Long ledgerAmount = 0L;

    private String ledgerDescription;

    private Long ledgerCategoryId;
}
