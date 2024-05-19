package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.util.DateTimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class MonthlyLedgerResponseDto {
    private int year;
    private int month;
    private Long totalAmount = 0L;
    private Map<Integer, List<MonthlyLedgerDto>> ledgers;

    public void setYearMonth(String yearMonth) {
        this.year = Integer.parseInt(yearMonth.substring(0, 4));
        this.month = Integer.parseInt(yearMonth.substring(5, 6));
    }

}
