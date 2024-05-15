package com.bb.accountbook.domain.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class CoupleMonthlyLedgerDto extends MonthlyLedgerDto {
    private String ownerNickname;

    public CoupleMonthlyLedgerDto(String ownerNickname, String ledgerCodeValue, LocalDate date, Long amount, String description) {
        super(ledgerCodeValue, date, amount, description);
        this.ownerNickname = ownerNickname;
    }
}
