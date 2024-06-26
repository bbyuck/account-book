package com.bb.accountbook.domain.ledger.repository.custom.impl;

import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.common.model.status.UserCoupleStatus;
import com.bb.accountbook.common.model.status.UserStatus;
import com.bb.accountbook.domain.ledger.repository.custom.LedgerCustomRepository;
import com.bb.accountbook.entity.Ledger;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LedgerCustomRepositoryImpl implements LedgerCustomRepository {

    private final EntityManager em;

    @Override
    public List<Ledger> findPersonalPeriodLedger(Long userId, LocalDate startDate, LocalDate endDate) {
        String jpql = "select l " +
                "from Ledger l " +
                "join fetch User u " +
                "on l.owner = u " +
                "where u.id = :userId " +
                "and l.date >= :startDate and l.date <= :endDate " +
                "order by l.date asc";
        return em.createQuery(jpql, Ledger.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    @Override
    public List<Ledger> findCouplePeriodLedger(Long coupleId, LocalDate startDate, LocalDate endDate) {
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
                "and l.date >= :startDate and l.date <= :endDate " +
                "and u.status = :userStatus " +
                "order by l.date asc";
        return em.createQuery(jpql, Ledger.class)
                .setParameter("coupleId", coupleId)
                .setParameter("userCoupleStatus", UserCoupleStatus.ACTIVE)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("userStatus", UserStatus.ACTIVE)
                .getResultList();
    }

    @Override
    public Optional<Ledger> findLedgerWithUserCouple(Long coupleId, Long ledgerId) {
        String jpql = "select l " +
                "from Ledger l " +
                "join fetch User u " +
                "on l.owner = u " +
                "join fetch UserCouple uc " +
                "on uc.user = u " +
                "join fetch Couple c " +
                "on uc.couple = c " +
                "where c.id = :coupleId " +
                "and l.id = :ledgerId " +
                "and u.status = :userStatus " +
                "order by l.date asc";
        return em.createQuery(jpql, Ledger.class)
                .setParameter("coupleId", coupleId)
                .setParameter("ledgerId", ledgerId)
                .setParameter("userStatus", UserStatus.ACTIVE)
                .getResultList().stream().findFirst();
    }

    @Override
    public List<Ledger> findPersonalSavings(Long userId) {
        String jpql = "select l " +
                "from Ledger l " +
                "join fetch User u " +
                "on l.owner = u " +
                "where u.id = :userId " +
                "and l.code = :ledgerCode " +
                "order by l.date asc";

        return em.createQuery(jpql, Ledger.class)
                .setParameter("userId", userId)
                .setParameter("ledgerCode", LedgerCode.S)
                .getResultList();
    }

    @Override
    public List<Ledger> findPersonalSavingsByEmail(String email) {
        String jpql = "select l " +
                "from Ledger l " +
                "join fetch User u " +
                "on l.owner = u " +
                "where u.email = :email " +
                "and l.code = :ledgerCode " +
                "order by l.date asc";
        return em.createQuery(jpql, Ledger.class)
                .setParameter("email", email)
                .setParameter("ledgerCode", LedgerCode.S)
                .getResultList();
    }

    @Override
    public List<Ledger> findCoupleSavings(Long coupleId) {
        String jpql = "select l " +
                "from Ledger l " +
                "join fetch User u " +
                "on l.owner = u " +
                "join fetch UserCouple uc " +
                "on uc.user = u " +
                "join fetch Couple c " +
                "on uc.couple = c " +
                "where c.id = :coupleId " +
                "and l.code = :ledgerCode " +
                "order by l.date asc";

        return em.createQuery(jpql, Ledger.class)
                .setParameter("coupleId", coupleId)
                .setParameter("ledgerCode", LedgerCode.S)
                .getResultList();
    }



    @Override
    public List<Ledger> findPersonalLedgers(String email) {
        String jpql = "select l " +
                "from Ledger l " +
                "join fetch User u " +
                "on l.owner = u " +
                "where u.email = :email " +
                "and u.status = :userStatus " +
                "order by l.date asc";

        return em.createQuery(jpql, Ledger.class)
                .setParameter("userStatus", UserStatus.ACTIVE)
                .setParameter("email", email)
                .getResultList();
    }

    @Override
    public List<Ledger> findCoupleLedgers(Long coupleId) {
        String jpql = "select l " +
                "from Ledger l " +
                "join fetch User u " +
                "on l.owner = u " +
                "join fetch UserCouple uc " +
                "on uc.user = u " +
                "join fetch Couple c " +
                "on uc.couple = c " +
                "where c.id = :coupleId";
        return em.createQuery(jpql, Ledger.class)
                .setParameter("coupleId", coupleId)
                .getResultList();
    }

    @Override
    public List<Ledger> findPersonalPeriodLedgerByEmail(String email, LocalDate startDate, LocalDate endDate) {
        String jpql = "select l " +
                "from Ledger l " +
                "join fetch User u " +
                "on l.owner = u " +
                "where u.email = :email " +
                "and l.date >= :startDate and l.date <= :endDate " +
                "order by l.date asc";
        return em.createQuery(jpql, Ledger.class)
                .setParameter("email", email)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    @Override
    public Optional<Ledger> findLedgerByIdAndUserEmail(Long ledgerId, String email) {
        String jpql = "select l " +
                "from Ledger l " +
                "join fetch User u " +
                "on l.owner = u " +
                "where l.id = :ledgerId " +
                "and u.email = :email";
        return em.createQuery(jpql, Ledger.class)
                .setParameter("ledgerId", ledgerId)
                .setParameter("email", email)
                .getResultList().stream().findFirst();
    }

    @Override
    public int clearLedgerCategories(Long ledgerCategoryId) {
        String jpql = "update Ledger l " +
                "set l.ledgerCategory = null " +
                "where l.ledgerCategory.id = :ledgerCategoryId";

        return em.createQuery(jpql)
                .setParameter("ledgerCategoryId", ledgerCategoryId)
                .executeUpdate();
    }
}
