package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import lombok.Data;

import java.util.List;

@Data
public class DailyLedgerDto {

    private List<LedgerDto> ledgers;
    private Long dailyIncome = 0L;
    private Long dailyExpenditure = 0L;
    private Long dailySave = 0L;


    public void addDailyIncome(Long amount) {
        this.dailyIncome += amount;
    }

    public void addDailyExpenditure(Long amount) {
        this.dailyExpenditure += amount;
    }

    public void addDailySave(Long amount) {
        this.dailySave += amount;
    }
}
