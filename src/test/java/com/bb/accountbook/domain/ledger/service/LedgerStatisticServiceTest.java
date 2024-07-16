package com.bb.accountbook.domain.ledger.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerCategoryStatistic;
import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerRequestDto;
import com.bb.accountbook.domain.ledger.dto.PeriodLedgerCodeStatistic;
import com.bb.accountbook.domain.ledger.dto.PeriodLedgerStatisticRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LedgerStatisticServiceTest {

    @Autowired
    LedgerStatisticService ledgerStatisticService;

    @Test
    @DisplayName("커플 월 - 카테고리 통계 조회")
    public void findMonthlyCategoryStatistic() throws Exception {
        // given
        LedgerCode targetCode = LedgerCode.E;
        String yearMonth = "202404";
        String email = "woman4@naver.com";

        MonthlyLedgerRequestDto requestDto = MonthlyLedgerRequestDto.builder()
                .email(email)
                .yearMonth(yearMonth)
                .ledgerCode(targetCode)
                .build();

        // when
        MonthlyLedgerCategoryStatistic statistic = ledgerStatisticService.findCategoryStatisticPerLedgerCode(requestDto);

        // then
        assertThat(statistic.getExpenditure()).isEqualTo(300000L);
        assertThat(statistic.getAmountsPerCategory().size()).isEqualTo(1);
        assertThat(statistic.getAmountsPerCategory().get(0).getCategory().getLedgerCategoryName()).isEqualTo("카테고리 없음");
    }

    @Test
    @DisplayName("월단위 기간 조회")
    public void findPeriodStatisticPerMonth() throws Exception {
        // given
        String email = "man3@naver.com";
        int startYear = 2024;
        int startMonth = 2;

        int endYear = 2024;
        int endMonth = 5;

        int invalidEndYear = 2025;
        int invalidEndMonth = 4;

        // when
        PeriodLedgerCodeStatistic statistic = ledgerStatisticService.findPeriodStatisticPerLedgerCode(
                PeriodLedgerStatisticRequestDto
                        .builder()
                        .email(email)
                        .startYear(startYear)
                        .startMonth(startMonth)
                        .endYear(endYear)
                        .endMonth(endMonth)
                        .build()
        );

        // then
        assertThrows(GlobalException.class, () -> ledgerStatisticService.findPeriodStatisticPerLedgerCode(
                PeriodLedgerStatisticRequestDto
                        .builder()
                        .email(email)
                        .startYear(startYear)
                        .startMonth(startMonth)
                        .endYear(invalidEndYear)
                        .endMonth(invalidEndMonth)
                        .build()
        ));
        assertThat(statistic.getMonthlyAmounts().size()).isEqualTo(1);
    }

}