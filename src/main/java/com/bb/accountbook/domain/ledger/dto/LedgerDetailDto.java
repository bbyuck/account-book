package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public abstract class LedgerDetailDto {
    protected Long ledgerId;
    protected LedgerCode ledgerCode;
    protected Long ledgerAmount;
    protected String ledgerDescription;

    protected int year;
    protected int month;
    protected int day;
    protected int dayOfWeek;

    protected LedgerDetailDto(Long ledgerId, LedgerCode ledgerCode, LocalDate ledgerDate, Long ledgerAmount, String ledgerDescription) {
        this.ledgerId = ledgerId;
        this.ledgerCode = ledgerCode;
        this.ledgerAmount = ledgerAmount;
        this.ledgerDescription = ledgerDescription;

        this.year = ledgerDate.getYear();
        this.month = ledgerDate.getMonthValue();
        this.day = ledgerDate.getDayOfMonth();
        this.dayOfWeek = ledgerDate.getDayOfWeek().getValue();
    }
}
