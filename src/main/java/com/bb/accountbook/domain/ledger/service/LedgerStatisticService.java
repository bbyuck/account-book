package com.bb.accountbook.domain.ledger.service;


import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerCategoryStatistic;
import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerRequestDto;
import com.bb.accountbook.domain.ledger.dto.PeriodLedgerCodeStatistic;
import com.bb.accountbook.domain.ledger.dto.PeriodLedgerStatisticRequestDto;

/**
 * 가계부 통계 서비스
 */
public interface LedgerStatisticService {

    /**
     * 월단위 가계부 코드 별 카테고리 통계
     * 도넛 그래프
     *
     * 가계부 코드, yyyyMM 입력
     * @param requestDto
     */
    MonthlyLedgerCategoryStatistic findCategoryStatisticPerLedgerCode(MonthlyLedgerRequestDto requestDto);


    /**
     * 연단위 월 별 가계부 코드 통계
     * 막대 그래프
     *
     * 수입/소득/저축 월 단위 변화 추이 확인
     * @param requestDto
     * @return
     */
    PeriodLedgerCodeStatistic findPeriodStatisticPerLedgerCode(PeriodLedgerStatisticRequestDto requestDto);


}
