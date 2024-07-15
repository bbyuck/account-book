package com.bb.accountbook.domain.ledger.service;

import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerCategoryStatistic;
import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerRequestDto;
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

}