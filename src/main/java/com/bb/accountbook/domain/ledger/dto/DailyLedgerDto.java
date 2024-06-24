package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DailyLedgerDto {

    private List<LedgerDto> ledgers = new ArrayList<>();
    private Long dailyIncome = 0L;
    private Long dailyExpenditure = 0L;
    private Long dailySave = 0L;

    public void addLedger(LedgerDto ledgerDto) {
        ledgers.add(ledgerDto);

        switch(ledgerDto.getLedgerCode()) {
            case E -> this.dailyExpenditure += ledgerDto.getAmount();
            case I -> this.dailyIncome += ledgerDto.getAmount();
            case S -> this.dailySave += ledgerDto.getAmount();
        }
    }

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
