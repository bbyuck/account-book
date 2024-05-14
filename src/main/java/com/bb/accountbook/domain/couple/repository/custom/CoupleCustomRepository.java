package com.bb.accountbook.domain.couple.repository.custom;

import com.bb.accountbook.entity.Couple;
import com.bb.accountbook.entity.User;
import com.bb.accountbook.entity.UserCouple;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoupleCustomRepository {
    UserCouple saveUserCouple(UserCouple userCouple);
    UserCouple findUserCouple(Long userCoupleId);
    boolean isExistUserCouple(User user, Couple couple);
    List<UserCouple> findUserCouplesByCoupleId(Long createdCoupleId);
    Couple findCoupleByUserId(Long userId);
}
