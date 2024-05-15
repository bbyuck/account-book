package com.bb.accountbook.domain.ledger.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersonalMonthlyLedgerDto extends MonthlyLedgerDto {
    public PersonalMonthlyLedgerDto(String ledgerCodeValue, LocalDate date, Long amount, String description) {
        super(ledgerCodeValue, date, amount, description);
    }
}
