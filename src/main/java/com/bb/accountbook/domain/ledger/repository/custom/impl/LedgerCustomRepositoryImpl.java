package com.bb.accountbook.domain.ledger.repository.custom.impl;

import com.bb.accountbook.common.model.status.UserCoupleStatus;
import com.bb.accountbook.domain.ledger.repository.custom.LedgerCustomRepository;
import com.bb.accountbook.entity.Ledger;
import com.bb.accountbook.entity.UserCouple;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LedgerCustomRepositoryImpl implements LedgerCustomRepository {

    private final EntityManager em;

    @Override
    public List<Ledger> findPersonalMonthlyLedger(Long userId, LocalDate startDate, LocalDate endDate) {
        String jpql = "select l " +
                "from Ledger l " +
                "join fetch User u " +
                "on l.owner = u " +
                "where u.id = :userId " +
                "and l.date between :startDate and :endDate " +
                "order by l.date asc";
        return em.createQuery(jpql, Ledger.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    @Override
    public List<Ledger> findCoupleMonthlyLedger(Long coupleId, LocalDate startDate, LocalDate endDate) {
        String jpql = "select l " +
                "from Ledger l " +
                "join fetch User u " +
                "on l.owner = u " +
                "join fetch UserCouple uc " +
                "on uc.user = u " +
                "join fetch Couple c " +
                "on uc.couple = c " +
                "where c.id = :coupleId " +
                "and uc.status = :userCoupleStatus " +
                "and l.date between :startDate and :endDate " +
                "order by l.date asc";
        return em.createQuery(jpql, Ledger.class)
                .setParameter("coupleId", coupleId)
                .setParameter("userCoupleStatus", UserCoupleStatus.ACTIVE)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}
