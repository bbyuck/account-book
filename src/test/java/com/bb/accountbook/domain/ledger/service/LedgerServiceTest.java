package com.bb.accountbook.domain.ledger.service;

import com.bb.accountbook.common.model.codes.GenderCode;
import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.entity.Ledger;
import com.bb.accountbook.entity.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LedgerServiceTest {

    @Autowired
    LedgerService ledgerService;

    @Autowired
    EntityManager em;

    @BeforeEach
    public void initData() {
        User testUser1 = new User("test1@naver.com", "1234", GenderCode.M);
        em.persist(testUser1);

        User testUser2 = new User("test2@naver.com", "1234", GenderCode.M);
        em.persist(testUser2);

        User testUser3 = new User("test2@naver.com", "1234", GenderCode.W);
        em.persist(testUser3);
    }


    @Test
    @DisplayName("가계부 상세 항목 입력")
    public void insert() throws Exception {
        // given
        Long userId = 1L;

        // when
        Long insertedLedgerId = ledgerService.insertLedger(userId, LedgerCode.I, LocalDate.now(), 4000000L, "월급");

        // then
        Ledger savedLedger = ledgerService.findLedger(insertedLedgerId);

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

        Ledger ledger = ledgerService.findLedger(updatedLedgerId);
        // then

        assertThat(ledger.getDate()).isEqualTo(LocalDate.of(2024, 5, 1));
        assertThat(ledger.getCode()).isEqualTo(LedgerCode.S);
        assertThat(ledger.getAmount()).isEqualTo(500000);
        assertThat(ledger.getDescription()).isEqualTo("저축");

    }
}