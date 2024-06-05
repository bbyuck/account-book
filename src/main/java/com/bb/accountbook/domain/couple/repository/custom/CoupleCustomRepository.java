package com.bb.accountbook.domain.couple.repository.custom;

import com.bb.accountbook.entity.Couple;
import com.bb.accountbook.entity.UserCouple;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoupleCustomRepository {
    UserCouple saveUserCouple(UserCouple userCouple);
    Optional<UserCouple> findUserCoupleById(Long userCoupleId);
    Optional<UserCouple> findUserCoupleByUserId(Long userId);
    Optional<UserCouple> findUserCoupleByUserIdAndCoupleId(Long userId, Long coupleId);
    List<UserCouple> findUserCouplesByCoupleId(Long coupleId);
    List<UserCouple> findUserCouplesByUserId(Long userId);
    Optional<Couple> findCoupleByUserId(Long userId);
    Optional<Couple> findCoupleByUserEmail(String userEmail);
    Optional<UserCouple> findUserCoupleByUserEmailAndCoupleId(String userEmail, Long coupleId);
    Optional<UserCouple> findUserCoupleByUserEmail(String userEmail);
}
