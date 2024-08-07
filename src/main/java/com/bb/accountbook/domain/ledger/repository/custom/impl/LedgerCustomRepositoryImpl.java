package com.bb.accountbook.domain.ledger.repository.custom.impl;

import com.bb.accountbook.common.model.codes.CustomCode;
import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.common.model.status.UserCoupleStatus;
import com.bb.accountbook.common.model.status.UserStatus;
import com.bb.accountbook.domain.ledger.dto.*;
import com.bb.accountbook.domain.ledger.repository.custom.LedgerCustomRepository;
import com.bb.accountbook.entity.Ledger;
import com.bb.accountbook.entity.QIcon;
import com.bb.accountbook.entity.QLedger;
import com.bb.accountbook.entity.QLedgerCategory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.bb.accountbook.entity.QCouple.couple;
import static com.bb.accountbook.entity.QCustom.custom;
import static com.bb.accountbook.entity.QIcon.*;
import static com.bb.accountbook.entity.QLedger.ledger;
import static com.bb.accountbook.entity.QLedgerCategory.*;
import static com.bb.accountbook.entity.QUser.user;
import static com.bb.accountbook.entity.QUserCouple.userCouple;

@Repository
@RequiredArgsConstructor
public class LedgerCustomRepositoryImpl implements LedgerCustomRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

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
    public List<Ledger> findCouplePeriodLedger(Long coupleId, LocalDate startDate, LocalDate endDate, LedgerCode ledgerCode) {
        BooleanBuilder dynamicQueryBuilder = new BooleanBuilder();

        if (coupleId != null) {
            dynamicQueryBuilder.and(couple.id.eq(coupleId));
        }
        if (ledgerCode != null) {
            dynamicQueryBuilder.and(ledger.code.eq(ledgerCode));
        }
        if (startDate != null && endDate != null) {
            dynamicQueryBuilder
                    .and(ledger.date.goe(startDate))
                    .and(ledger.date.loe(endDate));
        }

        return queryFactory.selectFrom(ledger)
                .join(user).fetchJoin().on(user.eq(ledger.owner))
                .leftJoin(ledgerCategory).fetchJoin().on(ledgerCategory.eq(ledger.ledgerCategory))
                .leftJoin(icon).fetchJoin().on(icon.eq(ledgerCategory.icon))
                .leftJoin(custom).fetchJoin().on(custom.user.eq(user))
                .join(userCouple).fetchJoin().on(user.eq(userCouple.user))
                .join(couple).fetchJoin().on(couple.eq(userCouple.couple))
                .where(
                        dynamicQueryBuilder.and(userCouple.status.eq(UserCoupleStatus.ACTIVE))
                                .and(user.status.eq(UserStatus.ACTIVE))
                                .and(custom.isNull().or(custom.code.eq(CustomCode.COLOR)))
                )
                .orderBy(ledger.date.asc())
                .fetch();
    }

    @Override
    public List<Ledger> findPersonalPeriodLedgerByEmail(String email, LocalDate startDate, LocalDate endDate, LedgerCode ledgerCode) {
        BooleanBuilder dynamicQueryBuilder = new BooleanBuilder();

        if (StringUtils.hasText(email)) {
            dynamicQueryBuilder.and(user.email.eq(email));
        }

        if (ledgerCode != null) {
            dynamicQueryBuilder.and(ledger.code.eq(ledgerCode));
        }

        if (startDate != null && endDate != null) {
            dynamicQueryBuilder
                    .and(ledger.date.goe(startDate))
                    .and(ledger.date.loe(endDate));
        }

        return queryFactory.selectFrom(ledger)
                .join(user).fetchJoin().on(user.eq(ledger.owner))
                .leftJoin(ledgerCategory).fetchJoin().on(ledgerCategory.eq(ledger.ledgerCategory))
                .leftJoin(icon).fetchJoin().on(icon.eq(ledgerCategory.icon))
                .leftJoin(custom).fetchJoin().on(custom.user.eq(user))
                .where(
                        dynamicQueryBuilder.and(custom.isNull().or(custom.code.eq(CustomCode.COLOR)))
                )
                .orderBy(ledger.date.asc())
                .fetch();
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

    @Override
    public List<MonthlyAmountDto> findCouplePeriodMonthlyAmounts(Long coupleId, LocalDate startDate, LocalDate endDate) {
        return queryFactory.select(new QMonthlyAmountDto(ledger.date.year(), ledger.date.month(), ledger.amount.sum(), ledger.code))
                .from(ledger)
                .leftJoin(user).fetchJoin().on(user.eq(ledger.owner))
                .leftJoin(userCouple).fetchJoin().on(user.eq(userCouple.user))
                .leftJoin(couple).fetchJoin().on(couple.eq(userCouple.couple))
                .where(
                        couple.id.eq(coupleId)
                                .and(ledger.date.goe(startDate).and(ledger.date.loe(endDate)))
                )
                .groupBy(ledger.date.year(), ledger.date.month(), ledger.code)
                .orderBy(ledger.date.year().asc(), ledger.date.month().asc())
                .fetch();
    }

    @Override
    public List<MonthlyAmountDto> findPersonalPeriodMonthlyAmounts(String email, LocalDate startDate, LocalDate endDate) {
        return queryFactory.select(new QMonthlyAmountDto(ledger.date.year(), ledger.date.month(), ledger.amount.sum(), ledger.code))
                .from(ledger)
                .leftJoin(user).fetchJoin().on(user.eq(ledger.owner))
                .where(
                        user.email.eq(email)
                                .and(ledger.date.goe(startDate).and(ledger.date.loe(endDate)))
                )
                .groupBy(ledger.date.year(), ledger.date.month(), ledger.code)
                .orderBy(ledger.date.year().asc(), ledger.date.month().asc())
                .fetch();
    }
}
