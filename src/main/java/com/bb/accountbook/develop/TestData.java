package com.bb.accountbook.develop;

import com.bb.accountbook.common.model.codes.GenderCode;
import com.bb.accountbook.common.model.codes.RoleCode;
import com.bb.accountbook.common.model.status.UserCoupleStatus;
import com.bb.accountbook.domain.couple.repository.CoupleRepository;
import com.bb.accountbook.domain.couple.service.CoupleService;
import com.bb.accountbook.domain.user.repository.RoleRepository;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.entity.Couple;
import com.bb.accountbook.entity.Role;
import com.bb.accountbook.entity.User;
import com.bb.accountbook.entity.UserCouple;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Transactional
public class TestData {

    private final EntityManager em;
    private final UserService userService;
    private final CoupleService coupleService;
    private final RoleRepository roleRepository;
    private final CoupleRepository coupleRepository;

    public void init() {
        roleRepository.saveAllAndFlush(Arrays.stream(RoleCode.values()).map(Role::new).collect(Collectors.toList()));

        String email1 = "k941026h@naver.com";
        String password1 = "pass1";
        Long userId1 = userService.join(email1, password1, GenderCode.M);

        String email2 = "abc123@naver.com";
        String password2 = "pass2";
        Long userId2 = userService.join(email2, password2, GenderCode.W);

        String email3 = "man3@naver.com";
        String password3 = "pass1";
        Long userId3 = userService.join(email3, password3, GenderCode.M);

        String email4 = "woman4@naver.com";
        String password4 = "pass2";
        Long userId4 = userService.join(email4, password4, GenderCode.W);


        User user3 = userService.findUserById(userId3);
        User user4 = userService.findUserById(userId4);

        coupleService.connectToOpponent(user3.getId(), "woman4@naver.com", "히욱", "bubu");
    }
}
