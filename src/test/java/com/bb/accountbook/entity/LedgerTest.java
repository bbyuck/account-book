package com.bb.accountbook.entity;

import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.domain.ledger.service.impl.LedgerCategoryServiceImpl;
import com.bb.accountbook.domain.ledger.service.impl.LedgerServiceImpl;
import com.bb.accountbook.domain.user.repository.UserRepository;
import com.bb.accountbook.domain.user.service.UserService;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class LedgerTest {

    @Autowired
    EntityManager em;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LedgerServiceImpl ledgerService;

    @Autowired
    LedgerCategoryServiceImpl ledgerCategoryService;

    @Test
    @DisplayName("연관관계 매핑시 nullable 테스트")
    void testMapping() {
        // given
        String email = "user@test.net";


        // when
        Long ledgerId = ledgerService.insertLedger(email, LedgerCode.I, LocalDate.of(2024, 5, 12), 3000000L, "테스트");
        Ledger ledger = ledgerService.findLedgerById(ledgerId);

        // then
        Assertions.assertThat(ledger.getLedgerCategory()).isNull();
    }



}