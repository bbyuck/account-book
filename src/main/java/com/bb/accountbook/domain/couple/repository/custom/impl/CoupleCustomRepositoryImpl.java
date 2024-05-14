package com.bb.accountbook.domain.couple.repository.custom.impl;

import com.bb.accountbook.domain.couple.repository.custom.CoupleCustomRepository;
import com.bb.accountbook.entity.Couple;
import com.bb.accountbook.entity.User;
import com.bb.accountbook.entity.UserCouple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class CoupleCustomRepositoryImpl implements CoupleCustomRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    @Override
    public UserCouple saveUserCouple(UserCouple userCouple) {
        em.persist(userCouple);
        return userCouple;
    }

    @Override
    public UserCouple findUserCouple(Long userCoupleId) {
        return em.find(UserCouple.class, userCoupleId);
    }

    @Override
    public Couple findCoupleByUserId(Long userId) {

        return null;
    }

    @Override
    public boolean isExistUserCouple(User user, Couple couple) {
        return false;
    }

    @Override
    public List<UserCouple> findUserCouplesByCoupleId(Long createdCoupleId) {
        return null;
    }

//    @Override
//    public boolean isExistUserCouple(User user, Couple Couple) {
//        Long count = queryFactory
//                .select(QUserCouple.userCouple.count())
//                .from(userGroup)
//                .join(QUser.user, userGroup.user)
//                .fetchJoin()
//                .join(QGroup.group, userGroup.group)
//                .fetchJoin()
//                .where(
//                        userGroup.user.eq(user),
//                        userGroup.group.eq(Couple)
//                ).fetchOne();
//        count = count == null ? 0L : count;
//        return count > 0;
//    }
//
//    @Override
//    public List<UserCouple> findUserCouplesByCoupleId(Long createdCoupleId) {
//        return queryFactory.selectFrom(userCouple)
//                .where(userCouple.group.id.eq(createdCoupleId))
//                .fetch();
//    }
}
