package com.bb.accountbook.domain.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MonthlyLedgerResponseDto {
    private int year;
    private int month;
    private Long totalAmount = 0L;
    private List<MonthlyLedgerDto> ledgers;
}
