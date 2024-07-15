package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.entity.LedgerCategory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
public class MonthlyLedgerCategoryStatistic {

    /**
     * {
     * save : Long,
     * income: Long,
     * expenditure: Long,
     * ledgerCode: LedgerCode,
     *     TODO 1. amount 기준 sort
     *     TODO 2. 최대 5개 카테고리까지
     *     amountsPerCategory: [
     *          {
     *              category: LedgerCategoryDto,
     *              amount: Long
     *          } ...
     *     ],
     *     topCount: 5
     * }
     */

    private Long save;
    private Long income;
    private Long expenditure;

    @Setter
    private List<AmountPerCategory> amountsPerCategory;
    private int topCount;

    public MonthlyLedgerCategoryStatistic(int topCount) {
        this.topCount = topCount;
        this.save = 0L;
        this.income = 0L;
        this.expenditure = 0L;
        this.amountsPerCategory = new ArrayList<>();
    }

    public void addSave(Long amount) {
        this.save += amount;
    }

    public void addExpenditure(Long amount) {
        this.expenditure += amount;
    }

    public void addIncome(Long amount) {
        this.income += amount;
    }



    @Data
    public static class AmountPerCategory {
        private LedgerCategoryDto category;
        private Long amount;

        public AmountPerCategory(LedgerCategory ledgerCategory) {
            this.category = new LedgerCategoryDto(ledgerCategory);
            this.amount = 0L;
        }

        public AmountPerCategory(LedgerCode ledgerCode) {
            this.category = new LedgerCategoryDto(null, "카테고리 없음", null, null, ledgerCode);
            this.amount = 0L;
        }

        public void add(Long amount) {
            this.amount += amount;
        }
    }

}
