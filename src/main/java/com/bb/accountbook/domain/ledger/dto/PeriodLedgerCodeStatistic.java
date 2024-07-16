package com.bb.accountbook.domain.ledger.dto;

import lombok.Data;

import java.util.*;

@Data
public class PeriodLedgerCodeStatistic {

    private int startYear;
    private int startMonth;
    private int endYear;
    private int endMonth;

    private Long periodIncome = 0L;
    private Long periodExpenditure = 0L;
    private Long periodSave = 0L;
    private List<MonthlyAmount> monthlyAmounts = new ArrayList<>();

    public static String getKey(int year, int month) {
        return year + "-" + month;
    }

    public void addIncome(Long amount) {
        periodIncome += amount;
    }

    public void addExpenditure(Long amount) {
        periodExpenditure += amount;
    }

    public void addSave(Long amount) {
        periodSave += amount;
    }

    private String key(int year, int month) {
        return year + "-" + month;
    }

    public PeriodLedgerCodeStatistic(int startYear, int startMonth, int endYear, int endMonth) {
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.endYear = endYear;
        this.endMonth = endMonth;


    }

    @Data
    public static class MonthlyAmount {
        private int year;
        private int month;
        private Long monthlyIncome;
        private Long monthlyExpenditure;
        private Long monthlySave;

        public MonthlyAmount(int year, int month) {
            this.year = year;
            this.month = month;
            this.monthlyIncome = 0L;
            this.monthlyExpenditure = 0L;
            this.monthlySave = 0L;
        }

        public void addIncome(Long amount) {
            monthlyIncome += amount;
        }

        public void addExpenditure(Long amount) {
            monthlyExpenditure += amount;
        }

        public void addSave(Long amount) {
            monthlySave += amount;
        }
    }
}
