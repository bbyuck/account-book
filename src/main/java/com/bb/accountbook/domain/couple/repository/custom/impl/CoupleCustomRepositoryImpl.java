package com.bb.accountbook.domain.couple.repository.custom.impl;

import com.bb.accountbook.common.model.status.UserCoupleStatus;
import com.bb.accountbook.domain.couple.repository.custom.CoupleCustomRepository;
import com.bb.accountbook.entity.Couple;
import com.bb.accountbook.entity.UserCouple;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public Optional<Couple> findCoupleByUserEmail(String userEmail) {
        String jpql = "select c " +
                "from Couple c " +
                "join fetch UserCouple uc " +
                "on c = uc.couple " +
                "join fetch User u " +
                "on u = uc.user " +
                "where u.email = :userEmail";
        return em.createQuery(jpql, Couple.class)
                .setParameter("userEmail", userEmail)
                .getResultList().stream().findFirst();
    }

    @Override
    public Optional<UserCouple> findUserCoupleByUserEmailAndCoupleId(String userEmail, Long coupleId) {
        String jpql = "select uc " +
                "from UserCouple uc " +
                "join fetch User u " +
                "on uc.user = u " +
                "join fetch Couple c " +
                "on uc.couple = c " +
                "where u.email = :userEmail " +
                "and c.id = :coupleId";
        return em.createQuery(jpql, UserCouple.class)
                .setParameter("userEmail", userEmail)
                .setParameter("coupleId", coupleId)
                .getResultList().stream().findFirst();
    }

    @Override
    public Optional<UserCouple> findUserCoupleByUserEmail(String userEmail) {
        String jpql = "select uc " +
                "from UserCouple uc " +
                "join fetch User u " +
                "on uc.user = u " +
                "join fetch Couple c " +
                "on uc.couple = c " +
                "where u.email = :userEmail";

        return em.createQuery(jpql, UserCouple.class)
                .setParameter("userEmail", userEmail)
                .getResultList().stream().findFirst();
    }

    @Override
    public Optional<UserCouple> findUserCoupleByUserEmailAndStatus(String email, UserCoupleStatus status) {
        String jpql = "select uc " +
                "from UserCouple uc " +
                "join fetch User u " +
                "on uc.user = u " +
                "where u.email = :email " +
                "and uc.status = :status";
        return em.createQuery(jpql, UserCouple.class)
                .setParameter("email", email)
                .setParameter("status", status)
                .getResultList().stream().findFirst();
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

        return em.createQuery(jpql, UserCouple.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Optional<UserCouple> findUserCoupleByUserIdAndCoupleId(Long userId, Long coupleId) {
        String jpql = "select uc " +
                "from UserCouple uc " +
                "where uc.user.id = :userId " +
                "and uc.couple.id = :coupleId";

        return em.createQuery(jpql, UserCouple.class)
                .setParameter("userId", userId)
                .setParameter("coupleId", coupleId)
                .getResultList().stream().findFirst();
    }

    @Override
    public Optional<UserCouple> findUserCoupleById(Long userCoupleId) {
        return Optional.of(em.find(UserCouple.class, userCoupleId));
    }

    @Override
    public Optional<UserCouple> findUserCoupleByUserId(Long userId) {
        String jpql = "select uc " +
                "from UserCouple uc " +
                "join fetch User u " +
                "on uc.user = u " +
                "where u.id = :userId";
        return em.createQuery(jpql, UserCouple.class)
                .setParameter("userId", userId)
                .getResultList().stream().findFirst();
    }
}
