package com.bb.accountbook.develop;

import com.bb.accountbook.common.model.codes.CustomCode;
import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.common.model.codes.RoleCode;
import com.bb.accountbook.common.model.status.UserStatus;
import com.bb.accountbook.domain.couple.repository.CoupleRepository;
import com.bb.accountbook.domain.couple.service.CoupleService;
import com.bb.accountbook.domain.custom.service.CustomService;
import com.bb.accountbook.domain.icon.service.IconService;
import com.bb.accountbook.domain.ledger.service.LedgerCategoryService;
import com.bb.accountbook.domain.ledger.service.LedgerService;
import com.bb.accountbook.domain.user.repository.RoleRepository;
import com.bb.accountbook.domain.user.repository.UserRoleRepository;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.entity.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Profile({"local", "dev", "default"})
@RequiredArgsConstructor
@Component
@Transactional
public class TestData {

    private final EntityManager em;
    private final UserService userService;
    private final CoupleService coupleService;
    private final LedgerService ledgerService;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final CoupleRepository coupleRepository;
    private final CustomService customService;
    private final IconService iconService;
    private final LedgerCategoryService ledgerCategoryService;


    public void init() {
        roleRepository.saveAllAndFlush(Arrays.stream(RoleCode.values()).map(Role::new).collect(Collectors.toList()));
    }

    public void createIcons() {
        List<String> icons = List.of("baby-carriage",
                "bag-shopping",
                "basket-shopping",
                "bowl-rice",
                "bus",
                "cake-candles",
                "car",
                "cart-shopping",
                "charging-station",
                "chart-line",
                "chart-pie",
                "computer",
                "credit-card",
                "dumbbell",
                "gamepad",
                "gift",
                "heart",
                "hospital",
                "house-chimney",
                "kit-medical",
                "martini-glass-citrus",
                "mug-hot",
                "plus",
                "spoon",
                "square-plus",
                "suitcase-rolling",
                "taxi",
                "train-subway",
                "won-sign");

        icons.forEach(iconService::insertIcon);
    }

    public void createAdmin() {
        String email = "admin@naver.com";
        String password = "1q2w3e4R!@";
        Long userId = userService.signup(email, password, password);
        User user = userService.findUserById(userId);
        Role role = roleRepository.findByCode(RoleCode.ROLE_ADMIN).orElseThrow(() -> new IllegalStateException("Role Entity 없음"));

        UserRole userRole = new UserRole(user, role);
        userRoleRepository.save(userRole);
    }

    public void initUsers() {
        String email1 = "k941026h@naver.com";
        String password1 = "1q2w3e4R!@";
        Long userId1 = userService.signup(email1, password1, password1);
        User userById = userService.findUserById(userId1);


        String email2 = "abc123@naver.com";
        String password2 = "1q2w3e4R!@";
        Long userId2 = userService.signup(email2, password2, password2);

        String email3 = "man3@naver.com";
        String password3 = "1q2w3e4R!@";
        Long userId3 = userService.signup(email3, password3, password3);

        String email4 = "woman4@naver.com";
        String password4 = "1q2w3e4R!@";
        Long userId4 = userService.signup(email4, password4, password4);

        String email5 = "user@test.net";
        String password5 = "1q2w3e4R!@";
        userService.signup(email5, password5, password5);


        User user3 = userService.findUserById(userId3);
        user3.changeStatus(UserStatus.ACTIVE);

        User user4 = userService.findUserById(userId4);
        user3.changeStatus(UserStatus.ACTIVE);

        coupleService.connectToOpponent(user3.getEmail(), "woman4@naver.com", "히욱", "bubu");
    }

    public void ledgerServiceTestData() {
        /**
         * 커플 생성
         */
        Long userCoupleId = coupleService.applyConnectRequest(2L, "아내");

        String manEmail = "man3@naver.com";
        ledgerService.insertLedger(manEmail, LedgerCode.I, LocalDate.of(2024, 4, 21), 4000000L, "M월급");
        ledgerService.insertLedger(manEmail, LedgerCode.E, LocalDate.of(2024, 4, 1), 100000L, "M소비1");
        ledgerService.insertLedger(manEmail, LedgerCode.E, LocalDate.of(2024, 4, 2), 200000L, "M소비2");
        ledgerService.insertLedger(manEmail, LedgerCode.S, LocalDate.of(2024, 4, 3), 150000L, "M저축1");
        ledgerService.insertLedger(manEmail, LedgerCode.S, LocalDate.of(2024, 4, 4), 220000L, "M저축2");

        String womanEmail = "woman4@naver.com";
        ledgerService.insertLedger(womanEmail, LedgerCode.I, LocalDate.of(2024, 4, 25), 4000000L, "W월급");
        ledgerService.insertLedger(womanEmail, LedgerCode.S, LocalDate.of(2024, 4, 5), 150000L, "W저축1");
        ledgerService.insertLedger(womanEmail, LedgerCode.S, LocalDate.of(2024, 4, 8), 220000L, "W저축2");

    }

    public void customTestData() {
        String email = "k941026h@naver.com";
        CustomCode code = CustomCode.COLOR;
        String value = "3399ff";
        customService.saveCustom(email, code, value);
    }

    public void createTestUserCategory(String email) {
        // icon별로 하나씩 추가해버리기
        User user = userService.findUserByEmail(email);
        LedgerCode[] codes = {LedgerCode.E, LedgerCode.S, LedgerCode.I};
        List<Icon> icons = iconService.findAllIcons();

        AtomicInteger i = new AtomicInteger();

        ledgerCategoryService.insertLedgerCategoryList(
                icons.stream().map(icon -> {
                    int idx = i.getAndIncrement();
                    return new LedgerCategory(user, "카테고리" + idx, codes[idx % 3], icon);
                }).collect(Collectors.toList()));
    }
}
