package com.bb.accountbook.domain.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyLedgerResponseDto {
    private String yearMonth;
    private Long totalAmount = 0L;
    private List<MonthlyLedgerDto> ledgers;

}
