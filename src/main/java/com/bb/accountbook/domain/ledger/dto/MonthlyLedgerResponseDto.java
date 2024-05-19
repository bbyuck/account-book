package com.bb.accountbook.domain.ledger.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class MonthlyLedgerResponseDto {
    private int year;
    private int month;
    private Long totalAmount = 0L;
    private Map<Integer, DailyLedgerDto> ledgersPerDay;

    public void setYearMonth(String yearMonth) {
        this.year = Integer.parseInt(yearMonth.substring(0, 4));
        this.month = Integer.parseInt(yearMonth.substring(5, 6));
    }

}
