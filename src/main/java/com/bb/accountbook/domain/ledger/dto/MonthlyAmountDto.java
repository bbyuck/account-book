package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MonthlyAmountDto {
    private int year;
    private int month;
    private Long amount;
    private LedgerCode ledgerCode;

    @QueryProjection
    public MonthlyAmountDto(int year, int month, Long amount, LedgerCode ledgerCode) {
        this.year = year;
        this.month = month;
        this.amount = amount;
        this.ledgerCode = ledgerCode;
    }
}
