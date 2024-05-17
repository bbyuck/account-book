package com.bb.accountbook.domain.ledger.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.develop.TestData;
import com.bb.accountbook.domain.ledger.dto.AssetDto;
import com.bb.accountbook.domain.ledger.dto.LedgerCoupleDetailDto;
import com.bb.accountbook.entity.Ledger;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LedgerServiceTest {

    @Autowired
    LedgerService ledgerService;

    @Autowired
    EntityManager em;

    @Autowired
    TestData testData;


    @Test
    @DisplayName("가계부 상세 항목 입력")
    public void insert() throws Exception {
        // given
        Long userId = 1L;

        // when
        Long insertedLedgerId = ledgerService.insertLedger(userId, LedgerCode.I, LocalDate.now(), 4000000L, "월급");

        // then
        Ledger savedLedger = ledgerService.findLedgerById(insertedLedgerId);

        assertThat(savedLedger.getAmount()).isEqualTo(4000000);
        assertThat(savedLedger.getCode()).isEqualTo(LedgerCode.I);
        assertThat(savedLedger.getDescription()).isEqualTo("월급");
    }

    @Test
    @DisplayName("가계부 상세 항목 수정")
    public void update() throws Exception {
        // given
        Long userId = 1L;
        Long insertedLedgerId = ledgerService.insertLedger(userId, LedgerCode.I, LocalDate.now(), 4000000L, "월급");


        // when
        Long updatedLedgerId = ledgerService.updateLedger(insertedLedgerId, LedgerCode.S, LocalDate.of(2024, 5, 1), 500000L, "저축");

        Ledger ledger = ledgerService.findLedgerById(updatedLedgerId);
        // then

        assertThat(ledger.getDate()).isEqualTo(LocalDate.of(2024, 5, 1));
        assertThat(ledger.getCode()).isEqualTo(LedgerCode.S);
        assertThat(ledger.getAmount()).isEqualTo(500000);
        assertThat(ledger.getDescription()).isEqualTo("저축");

    }

    @Test
    @DisplayName("월별 가계부 조회")
    public void personalMonthly() throws Exception {
        // given
        Long manId = 3L;
        Long womanId = 4L;

        // when
        List<Ledger> personalMonthlyLedger = ledgerService.findPersonalMonthlyLedger(manId, "202404");
        List<Ledger> coupleMonthlyLedger = ledgerService.findCoupleMonthlyLedger(womanId, "202404");


        // then
        Assertions.assertThrows(GlobalException.class, () -> ledgerService.findCoupleMonthlyLedger(1L,"202404"));
        assertThat(personalMonthlyLedger.size()).isEqualTo(5);
        assertThat(coupleMonthlyLedger.size()).isEqualTo(8);
        assertThat(coupleMonthlyLedger.get(0).getDate()).isEqualTo(LocalDate.of(2024, 4, 1));
        assertThat(personalMonthlyLedger.get(0).getDate()).isEqualTo(LocalDate.of(2024, 4, 1));
    }

    @Test
    @DisplayName("커플 가계부 상세 항목 조회")
    public void findCoupleLedger() throws Exception {
        // given
        Long manId = 3L;
        Long womanId = 4L;

        Long coupleId = 1L;

        Long manLedgerId = 2L;

        // when
        LedgerCoupleDetailDto coupleLedger = ledgerService.findCoupleLedger(coupleId, manLedgerId);

        // then

        assertThat(coupleLedger.getLedgerDate()).isEqualTo(LocalDate.of(2024, 4, 1));
        assertThat(coupleLedger.getOwnerNickname()).isEqualTo("히욱");
    }

    @Test
    @DisplayName("커플 자산 조회")
    public void findCoupleAsset() throws Exception {
        // given
        Long manId = 3L;

        // when
        AssetDto coupleAsset = ledgerService.findCoupleAsset(manId);

        // then
        assertThat(coupleAsset.getAmount()).isEqualTo(740000L);
    }
}