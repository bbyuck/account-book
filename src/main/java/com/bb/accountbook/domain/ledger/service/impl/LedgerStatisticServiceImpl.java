package com.bb.accountbook.domain.ledger.service.impl;

import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerCategoryStatistic;
import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerRequestDto;
import com.bb.accountbook.domain.ledger.service.LedgerCategoryService;
import com.bb.accountbook.domain.ledger.service.LedgerService;
import com.bb.accountbook.domain.ledger.service.LedgerStatisticService;
import com.bb.accountbook.entity.Ledger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LedgerStatisticServiceImpl implements LedgerStatisticService {

    private final LedgerService ledgerService;

    private final LedgerCategoryService ledgerCategoryService;

    private static final int STATISTIC_TOP_COUNT = 5;

    @Override
    @Transactional(readOnly = true)
    public MonthlyLedgerCategoryStatistic findCategoryStatisticPerLedgerCode(MonthlyLedgerRequestDto requestDto) {
        List<Ledger> monthlyLedgers = ledgerService.findMonthlyLedger(requestDto);

        MonthlyLedgerCategoryStatistic statistic = new MonthlyLedgerCategoryStatistic(STATISTIC_TOP_COUNT);
        Map<Long, MonthlyLedgerCategoryStatistic.AmountPerCategory> tempMap = new HashMap<>();

        for (Ledger ledger : monthlyLedgers) {
            switch (ledger.getCode()) {
                case I -> statistic.addIncome(ledger.getAmount());
                case E -> statistic.addExpenditure(ledger.getAmount());
                case S -> statistic.addSave(ledger.getAmount());
                default -> log.debug("잘못된 LedgerCode를 필드로 갖고 있습니다. ====== {}", ledger.getId());
            }

            if (ledger.getLedgerCategory() == null) {
                tempMap.putIfAbsent(
                        -1L,
                        new MonthlyLedgerCategoryStatistic.AmountPerCategory());
                tempMap.get(-1L).add(ledger.getAmount());
            }
            else {
                tempMap.putIfAbsent(
                        ledger.getLedgerCategory().getId()
                        , new MonthlyLedgerCategoryStatistic.AmountPerCategory(ledger.getLedgerCategory()));
                tempMap.get(ledger.getLedgerCategory().getId()).add(ledger.getAmount());
            }
        }

        statistic.setAmountsPerCategory(
                tempMap.values().stream().sorted(
                        (e1, e2) -> Long.compare(e2.getAmount(), e1.getAmount())
                ).collect(Collectors.toList())
        );


        return statistic;
    }
}
