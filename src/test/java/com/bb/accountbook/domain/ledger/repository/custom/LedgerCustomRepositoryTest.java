package com.bb.accountbook.domain.ledger.repository.custom;

import com.bb.accountbook.domain.ledger.repository.LedgerRepository;
import com.bb.accountbook.domain.ledger.service.LedgerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LedgerCustomRepositoryTest {

    @Autowired
    LedgerRepository ledgerRepository;

    @Autowired
    LedgerService ledgerService;

    @BeforeEach
    public void before() {
        Long testUserId = 3L;

    }

    @Test
    @DisplayName("개인 - 월별 가계부 항목 조회")
    public void personalMonthly() throws Exception {
        // given


        // when

        // then
    }
}