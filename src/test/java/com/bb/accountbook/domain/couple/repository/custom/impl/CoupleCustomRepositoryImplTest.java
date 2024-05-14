package com.bb.accountbook.domain.couple.repository.custom.impl;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.common.model.status.UserCoupleStatus;
import com.bb.accountbook.domain.couple.repository.CoupleRepository;
import com.bb.accountbook.entity.Couple;
import com.bb.accountbook.entity.UserCouple;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class CoupleCustomRepositoryImplTest {

    @Autowired
    CoupleRepository coupleRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("유저 ID로 Couple Entity 조회")
    public void findCoupleByUserId() throws Exception {
        // given
        Couple couple = coupleRepository.findCoupleByUserId(3L).orElseThrow(() -> new GlobalException(ErrorCode.ERR_CPL_001));
//        assertThrows(Exception.class, () -> coupleRepository.findCoupleByUserId(1L));
        // when

        // then
        Assertions.assertThat(couple.getName()).isEqualTo("bubu");
    }

    @Test
    @DisplayName("유저 - 커플 매핑 여부 조회")
    public void isExistUserCouple() throws Exception {
        // given
        Long userId = 3L;
        Long coupleId = 1L;


        // when
        boolean existUserCouple = coupleRepository.isExistUserCouple(userId, coupleId);

        // then
        Assertions.assertThat(existUserCouple).isTrue();

    }

    @Test
    @DisplayName("커플에 매핑되어 있는 유저 - 커플 엔티티 조회")
    public void findUserCouplesByCoupleId() throws Exception {
        // given
        Long coupleId = 1L;
        // when
        List<UserCouple> userCouples = coupleRepository.findUserCouplesByCoupleId(coupleId);

        // then
        Assertions.assertThat(userCouples.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("API 호출자와 매핑된 UserCouple 정보들 get")
    public void findUserCouplesByUserId() throws Exception {
        // given
        Long userId = 3L;

        // when
        List<UserCouple> userCouples = coupleRepository.findUserCouplesByUserId(userId);

        // then
        Assertions.assertThat(userCouples.size()).isEqualTo(2);
        Assertions.assertThat(userCouples.get(0).getNickname()).isEqualTo("히욱");
        Assertions.assertThat(userCouples.get(0).getStatus()).isEqualTo(UserCoupleStatus.ACTIVE);
    }
}