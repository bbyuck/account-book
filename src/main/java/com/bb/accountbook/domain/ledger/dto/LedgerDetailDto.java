package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class LedgerDetailDto {
    protected Long ledgerId;
    protected LedgerCode ledgerCode;
    protected LocalDate ledgerDate;
    protected Long ledgerAmount;
    protected String ledgerDescription;
}
