package com.bb.accountbook.domain.couple.service;

import com.bb.accountbook.common.model.status.UserCoupleStatus;
import com.bb.accountbook.domain.couple.repository.CoupleRepository;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.entity.User;
import com.bb.accountbook.entity.UserCouple;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@SpringBootTest
class CoupleServiceTest {

    @Autowired
    CoupleService coupleService;

    @Autowired
    CoupleRepository coupleRepository;

    @Autowired
    UserService userService;

    @Test
    @DisplayName("남 -> 여 커플로 연결 신청")
    public void connect() throws Exception {
        // given
        User man = userService.findUserByEmail("k941026h@naver.com");
        User woman = userService.findUserByEmail("abc123@naver.com");


        // when
        Long userCoupleId = coupleService.connectToOpponent(man.getId(), woman.getEmail(), "남편", "부부");
        List<UserCouple> userCouples = coupleService.findUserCouples(man.getId());

        List<UserCouple> waitList = userCouples.stream().filter(userCouple -> userCouple.getStatus() == UserCoupleStatus.WAIT).toList();
        Set<String> nicknameSet = userCouples.stream().map(UserCouple::getNickname).collect(Collectors.toSet());

        // then
        Assertions.assertThat(userCouples.size()).isEqualTo(2);
        Assertions.assertThat(nicknameSet).contains("남편");
        Assertions.assertThat(waitList.size()).isEqualTo(1);
    }
}