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

    public void addIncome(Long amount) {
        periodIncome += amount;
        monthlyAmounts.get(monthlyAmounts.size() - 1).addIncome(amount);
    }

    public void addExpenditure(Long amount) {
        periodExpenditure += amount;
        monthlyAmounts.get(monthlyAmounts.size() - 1).addExpenditure(amount);
    }

    public void addSave(Long amount) {
        periodSave += amount;
        monthlyAmounts.get(monthlyAmounts.size() - 1).addSave(amount);
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

        void addIncome(Long amount) {
            monthlyIncome += amount;
        }

        void addExpenditure(Long amount) {
            monthlyExpenditure += amount;
        }

        void addSave(Long amount) {
            monthlySave += amount;
        }

    }
}
