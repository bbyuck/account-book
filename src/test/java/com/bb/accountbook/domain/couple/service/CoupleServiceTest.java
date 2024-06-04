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
        Long userCoupleId = coupleService.connectToOpponent(man.getEmail(), woman.getEmail(), "남편", "부부");
        List<UserCouple> userCouples = coupleRepository.findUserCouplesByUserId(man.getId());

        List<UserCouple> waitList = userCouples.stream().filter(userCouple -> userCouple.getStatus() == UserCoupleStatus.WAIT).toList();
        Set<String> nicknameSet = userCouples.stream().map(UserCouple::getNickname).collect(Collectors.toSet());

        // then
        Assertions.assertThat(userCouples.size()).isEqualTo(2);
        Assertions.assertThat(nicknameSet).contains("남편");
        Assertions.assertThat(waitList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("남 -> 여 커플로 연결 신청 후 여자 계정으로 수락")
    public void applyConnectRequest() throws Exception {
        // given
        Long manId = 3L;
        Long manUserCoupleId = 1L;

        Long womanId = 4L;
        Long womanUserCoupleId = 2L;

        // when
        Long womanUserId = coupleService.applyConnectRequest(2L, "test");
        UserCouple userCouple = coupleService.findUserCouple(womanUserCoupleId);

        // then
        Assertions.assertThat(userCouple.getStatus()).isEqualTo(UserCoupleStatus.ACTIVE);
        Assertions.assertThat(userCouple.getNickname()).isEqualTo("test");
    }
}