package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.util.DateTimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@NoArgsConstructor
public class MonthlyLedgerResponseDto {
    private int year;
    private int month;
    private Long totalIncome = 0L;
    private Long totalExpenditure = 0L;
    private Long totalSave = 0L;
    private List<DailyLedgerDto> ledgersPerDay;

    public MonthlyLedgerResponseDto(String yearMonth) {
        this.year = Integer.parseInt(yearMonth.substring(0, 4));
        this.month = Integer.parseInt(yearMonth.substring(4, 6));

        int lastDay = DateTimeUtil.getMonthlyEndDate(year, month).getDayOfMonth();

        ledgersPerDay = IntStream.range(0, lastDay + 1)
                .mapToObj(i -> new DailyLedgerDto())
                .collect(Collectors.toList());
    }

    public void addTotalIncome(Long amount) {
        this.totalIncome += amount;
    }

    public void addTotalExpenditure(Long amount) {
        this.totalExpenditure += amount;
    }

    public void addTotalSave(Long amount) {
        this.totalSave += amount;
    }

}
