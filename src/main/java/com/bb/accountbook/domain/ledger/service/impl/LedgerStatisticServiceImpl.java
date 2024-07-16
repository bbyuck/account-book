package com.bb.accountbook.domain.ledger.service.impl;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.common.util.DateTimeUtil;
import com.bb.accountbook.domain.couple.service.CoupleService;
import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerCategoryStatistic;
import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerRequestDto;
import com.bb.accountbook.domain.ledger.dto.PeriodLedgerCodeStatistic;
import com.bb.accountbook.domain.ledger.dto.PeriodLedgerStatisticRequestDto;
import com.bb.accountbook.domain.ledger.repository.LedgerRepository;
import com.bb.accountbook.domain.ledger.service.LedgerService;
import com.bb.accountbook.domain.ledger.service.LedgerStatisticService;
import com.bb.accountbook.entity.Couple;
import com.bb.accountbook.entity.Ledger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LedgerStatisticServiceImpl implements LedgerStatisticService {

    private final CoupleService coupleService;

    private final LedgerService ledgerService;

    private final LedgerRepository ledgerRepository;

    private static final int STATISTIC_TOP_COUNT = 10;

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

    @Override
    @Transactional(readOnly = true)
    public PeriodLedgerCodeStatistic findPeriodStatisticPerLedgerCode(PeriodLedgerStatisticRequestDto requestDto) {
        PeriodLedgerCodeStatistic statistic = new PeriodLedgerCodeStatistic(
                requestDto.getStartYear()
                , requestDto.getStartMonth()
                , requestDto.getEndYear()
                , requestDto.getEndMonth());

        List<Ledger> periodLedgers;
        LocalDate startDate = DateTimeUtil.getMonthlyStartDate(requestDto.getStartYear(), requestDto.getStartMonth());
        LocalDate endDate = DateTimeUtil.getMonthlyEndDate(requestDto.getEndYear(), requestDto.getEndMonth());


        if (startDate.isAfter(endDate)) {
            log.debug("startDate가 endDate보다 이후입니다. ====== startDate={} / endDate={}", startDate, endDate);
            throw new GlobalException(ErrorCode.ERR_SYS_004);
        }
        if (DateTimeUtil.isOneYearOrMoreApart(startDate, endDate)) {
            log.debug("기간 조회 최대 기간은 1년입니다.====== startDate={} / endDate={}", startDate, endDate);
            throw new GlobalException(ErrorCode.ERR_SYS_005);
        }


        if (coupleService.isActiveCouple(requestDto.getEmail())) {
            Couple couple = coupleService.findCoupleByUserEmail(requestDto.getEmail());
            periodLedgers = ledgerRepository.findCouplePeriodLedger(couple.getId(), startDate, endDate, null);
        }
        else {
            periodLedgers = ledgerRepository.findPersonalPeriodLedgerByEmail(requestDto.getEmail(), startDate, endDate, null);
        }

        AtomicInteger currentYear = new AtomicInteger(requestDto.getStartYear());
        AtomicInteger currentMonth = new AtomicInteger(requestDto.getStartMonth());

        periodLedgers.stream().forEach(periodLedger -> {
            LocalDate ledgerDate = periodLedger.getDate();

            int year = ledgerDate.getYear();
            int month = ledgerDate.getMonthValue();

            PeriodLedgerCodeStatistic.MonthlyAmount monthlyAmount;

            if (year != currentYear.get() || month != currentMonth.get() || statistic.getMonthlyAmounts().isEmpty()) {
                monthlyAmount = new PeriodLedgerCodeStatistic.MonthlyAmount(year, month);
                statistic.getMonthlyAmounts().add(monthlyAmount);

                currentYear.set(year);
                currentMonth.set(month);
            }

            switch(periodLedger.getCode()) {
                case E -> statistic.addExpenditure(periodLedger.getAmount());
                case I -> statistic.addIncome(periodLedger.getAmount());
                case S -> statistic.addSave(periodLedger.getAmount());
                default -> {}
            }
        });

        return statistic;
    }
}
