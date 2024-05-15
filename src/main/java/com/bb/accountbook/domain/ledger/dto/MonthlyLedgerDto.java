package com.bb.accountbook.domain.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class MonthlyLedgerDto {
    protected String ledgerCodeValue;
    protected LocalDate date;
    protected Long amount;
    protected String description;
}
