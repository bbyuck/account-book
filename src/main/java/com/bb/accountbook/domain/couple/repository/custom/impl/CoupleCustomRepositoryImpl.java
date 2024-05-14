package com.bb.accountbook.domain.couple.repository.custom.impl;

import com.bb.accountbook.domain.couple.repository.custom.CoupleCustomRepository;
import com.bb.accountbook.entity.Couple;
import com.bb.accountbook.entity.UserCouple;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class CoupleCustomRepositoryImpl implements CoupleCustomRepository {

    private final EntityManager em;


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
    public Optional<Couple> findCoupleByUserId(Long userId) {
        String jpql = "select c " +
                "from Couple c " +
                "join fetch UserCouple uc " +
                "on c = uc.couple " +
                "join fetch User u " +
                "on u = uc.user " +
                "where u.id = :userId";

        return em.createQuery(jpql, Couple.class)
                .setParameter("userId", userId)
                .getResultList().stream().findFirst();
    }

    @Override
    public boolean isExistUserCouple(Long userId, Long coupleId) {
        String jpql2 = "select uc " +
                "from UserCouple uc " +
                "join fetch Couple c " +
                "on uc.couple = c " +
                "join fetch User u " +
                "on uc.user = u " +
                "where uc.user.id = :userId " +
                "and uc.couple.id = :coupleId";

        int count = em.createQuery(jpql2, UserCouple.class)
                .setParameter("userId", userId)
                .setParameter("coupleId", coupleId)
                .getResultList()
                .size();

        return count > 0;
    }

    @Override
    public List<UserCouple> findUserCouplesByCoupleId(Long coupleId) {
        String jpql = "select uc " +
                "from UserCouple uc " +
                "join fetch Couple c " +
                "on uc.couple = c " +
                "where c.id = :coupleId";

        return em.createQuery(jpql, UserCouple.class)
                .setParameter("coupleId", coupleId)
                .getResultList();
    }

    @Override
    public List<UserCouple> findUserCouplesByUserId(Long userId) {
        String jpql = "select c.userCouples " +
                "from Couple c " +
                "join fetch UserCouple uc " +
                "on uc.couple = c " +
                "join fetch User u " +
                "on uc.user = u " +
                "where u.id = :userId";

        return em.createQuery(jpql, UserCouple.class).setParameter("userId", userId).getResultList();
    }
}
