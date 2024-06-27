package com.bb.accountbook.domain.ledger.service;

import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.domain.user.repository.UserRepository;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.entity.Ledger;
import com.bb.accountbook.entity.LedgerCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static com.bb.accountbook.common.model.codes.LedgerCode.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LedgerCategoryServiceTest {

    @Autowired
    LedgerCategoryService ledgerCategoryService;

    @Autowired
    UserService userService;

    @Autowired
    LedgerService ledgerService;


    @BeforeEach
    public void createTestUser() {
        String email = "user@test.net";
        String password = "1q2w3e4R!@";
        userService.signup(email, password, password);
    }


    @Test
    @DisplayName("카테고리 생성")
    void insert() {
        // given
        String email = "user@test.net";
        String name1 = "배달비";
        LedgerCode ledgerCode1 = E;

        String name2 = "정기예금";
        LedgerCode ledgerCode2 = S;

        // when
        Long categoryId1 = ledgerCategoryService.insertLedgerCategory(email, name1, ledgerCode1);
        Long categoryId2 = ledgerCategoryService.insertLedgerCategory(email, name2, ledgerCode2);

        // then
        Assertions.assertThat(categoryId1).isNotNull();
        Assertions.assertThat(categoryId2).isNotNull();
    }

    @Test
    @DisplayName("카테고리 단건 조회")
    void findCategoryById() {
        // given
        String email = "user@test.net";
        String name1 = "배달비";
        LedgerCode ledgerCode1 = E;

        String name2 = "정기예금";
        LedgerCode ledgerCode2 = S;

        // when
        Long categoryId1 = ledgerCategoryService.insertLedgerCategory(email, name1, ledgerCode1);
        Long categoryId2 = ledgerCategoryService.insertLedgerCategory(email, name2, ledgerCode2);

        LedgerCategory category1 = ledgerCategoryService.findLedgerCategoryById(categoryId1);
        LedgerCategory category2 = ledgerCategoryService.findLedgerCategoryById(categoryId2);

        // then
        Assertions.assertThat(category1.getName()).isEqualTo("배달비");
        Assertions.assertThat(category1.getLedgerCode()).isEqualTo(E);
        Assertions.assertThat(category2.getName()).isEqualTo("정기예금");
        Assertions.assertThat(category2.getLedgerCode()).isEqualTo(S);
    }


    @Test
    @DisplayName("소유한 카테고리 조회 - 파라미터 없음")
    void findOwnLedgerCategoriesWithoutParam() {
        // given
        String email = "user@test.net";
        String name1 = "배달비";
        LedgerCode ledgerCode1 = E;

        String name2 = "정기예금";
        LedgerCode ledgerCode2 = S;

        // when
        ledgerCategoryService.insertLedgerCategory(email, name1, ledgerCode1);
        ledgerCategoryService.insertLedgerCategory(email, name2, ledgerCode2);
        List<LedgerCategory> ownLedgerCategories = ledgerCategoryService.findOwnLedgerCategories(email);

        // then
        Assertions.assertThat(ownLedgerCategories.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("연관관계 매핑 후 대상 테이블 Entity 제거 테스트")
    void testTargetTableEntityDeletion() {
        // given
        String email = "user@test.net";


        String name = "배달비";


        LedgerCategory category = ledgerCategoryService.findLedgerCategoryById(ledgerCategoryService.insertLedgerCategory(email, name, E));

        Long ledgerId1 = ledgerService.insertLedger(category, email, I, LocalDate.of(2024, 5, 12), 20000L, "배달비 테스트");
        Long ledgerId2 = ledgerService.insertLedger(category, email, E, LocalDate.of(2024, 3, 12), 50000L, "배달비 테스트2");

        // when
        ledgerCategoryService.deleteLedgerCategory(category.getId());

        // then
        Ledger ledger1 = ledgerService.findLedgerById(ledgerId1);
        Ledger ledger2 = ledgerService.findLedgerById(ledgerId2);

        Assertions.assertThat(ledger1.getLedgerCategory()).isNull();
        Assertions.assertThat(ledger2.getLedgerCategory()).isNull();
    }

}