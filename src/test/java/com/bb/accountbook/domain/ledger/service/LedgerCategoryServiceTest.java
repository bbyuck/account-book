package com.bb.accountbook.domain.ledger.service;

import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.develop.TestData;
import com.bb.accountbook.domain.couple.dto.CoupleConnectionInfoResponseDto;
import com.bb.accountbook.domain.couple.service.CoupleService;
import com.bb.accountbook.domain.user.repository.UserRepository;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.entity.Ledger;
import com.bb.accountbook.entity.LedgerCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    CoupleService coupleService;

    @Autowired
    TestData testData;

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
        Long categoryId1 = ledgerCategoryService.insertLedgerCategory(email, name1, ledgerCode1, 5L);
        Long categoryId2 = ledgerCategoryService.insertLedgerCategory(email, name2, ledgerCode2, 6L);

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
        Long categoryId1 = ledgerCategoryService.insertLedgerCategory(email, name1, ledgerCode1, 7L);
        Long categoryId2 = ledgerCategoryService.insertLedgerCategory(email, name2, ledgerCode2, 8L);

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
        String email = "k941026h@naver.com";
        String name1 = "배달비";
        LedgerCode ledgerCode1 = E;

        String name2 = "정기예금";
        LedgerCode ledgerCode2 = S;

        // when
        ledgerCategoryService.insertLedgerCategory(email, name1, ledgerCode1, 9L);
        ledgerCategoryService.insertLedgerCategory(email, name2, ledgerCode2, 10L);
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

        Long categoryId = ledgerCategoryService.insertLedgerCategory(email, name, E, 1L);

        Long ledgerId1 = ledgerService.insertLedger(categoryId, email, I, LocalDate.of(2024, 5, 12), 20000L, "배달비 테스트");
        Long ledgerId2 = ledgerService.insertLedger(categoryId, email, E, LocalDate.of(2024, 3, 12), 50000L, "배달비 테스트2");

        // when
        ledgerCategoryService.deleteLedgerCategory(categoryId);

        // then
        Ledger ledger1 = ledgerService.findLedgerById(ledgerId1);
        Ledger ledger2 = ledgerService.findLedgerById(ledgerId2);

        Assertions.assertThat(ledger1.getLedgerCategory()).isNull();
        Assertions.assertThat(ledger2.getLedgerCategory()).isNull();
    }

    @Test
    @DisplayName("커플일 경우 커플 소유 카테고리 전체 조회")
    void findCoupleOwnCategories() {
        // given
        String email2 = "coupleuser2@test.net";
        String password = "1q2w3e4R!@";
        userService.signup(email2, password, password);

        String email1 = "coupleuser1@test.net";
        userService.signup(email1, password, password);

        // when
        coupleService.connectToOpponent(email1, email2, "caller", "coup");

        CoupleConnectionInfoResponseDto coupleConnectionInfo = coupleService.findCoupleConnectionInfo(email2);
        coupleService.applyConnectRequest(coupleConnectionInfo.getUserCoupleId(), "oppo");

        String name1 = "배달비";
        LedgerCode ledgerCode1 = E;

        String name2 = "정기예금";
        LedgerCode ledgerCode2 = S;

        String name3 = "교통비";
        LedgerCode ledgerCode3 = E;

        String name4 = "월급";
        LedgerCode ledgerCode4 = I;

        // when
        Long categoryId1 = ledgerCategoryService.insertLedgerCategory(email2, name1, ledgerCode1, 1L);
        Long categoryId2 = ledgerCategoryService.insertLedgerCategory(email2, name2, ledgerCode2, 2L);
        Long categoryId3 = ledgerCategoryService.insertLedgerCategory(email1, name3, ledgerCode3, 3L);
        Long categoryId4 = ledgerCategoryService.insertLedgerCategory(email1, name4, ledgerCode4, 4L);

        LedgerCategory category1 = ledgerCategoryService.findLedgerCategoryById(categoryId1);
        LedgerCategory category2 = ledgerCategoryService.findLedgerCategoryById(categoryId2);
        LedgerCategory category3 = ledgerCategoryService.findLedgerCategoryById(categoryId3);
        LedgerCategory category4 = ledgerCategoryService.findLedgerCategoryById(categoryId4);

        //

        // then
        int size1 = ledgerCategoryService.findOwnLedgerCategories(email1).size();
        Assertions.assertThat(size1).isEqualTo(4);
        int size2 = ledgerCategoryService.findOwnLedgerCategories(email2).size();
        Assertions.assertThat(size2).isEqualTo(4);


    }
}