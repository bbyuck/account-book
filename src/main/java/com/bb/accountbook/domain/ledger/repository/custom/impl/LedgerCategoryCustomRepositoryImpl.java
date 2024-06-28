package com.bb.accountbook.domain.ledger.repository.custom.impl;

import com.bb.accountbook.domain.ledger.repository.custom.LedgerCategoryCustomRepository;
import com.bb.accountbook.entity.LedgerCategory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LedgerCategoryCustomRepositoryImpl implements LedgerCategoryCustomRepository {

    private final EntityManager em;

    @Override
    public List<LedgerCategory> findByOwnerEmail(String email) {
        String jpql = "select lc " +
                "from LedgerCategory lc " +
                "join fetch User u " +
                "on lc.owner = u " +
                "where u.email = :email";

        return em.createQuery(jpql, LedgerCategory.class)
                .setParameter("email", email)
                .getResultList();
    }

    @Override
    public List<LedgerCategory> findCoupleOwnCategoriesByOwnerEmail(Long coupleId) {
        String jpql = "select lc " +
                "from LedgerCategory lc " +
                "join fetch User u " +
                "on lc.owner = u " +
                "where u.id in (" +
                "   select uc.user.id " +
                "   from UserCouple uc " +
                "   join Couple c " +
                "   on uc.couple = c " +
                "   where c.id = :coupleId" +
                ")";
        return em.createQuery(jpql, LedgerCategory.class)
                .setParameter("coupleId", coupleId)
                .getResultList();
    }

    @Override
    public Optional<LedgerCategory> findByOwnerEmailAndId(String email, Long id) {
        String jpql = "select lc " +
                "from LedgerCategory lc " +
                "join fetch User u " +
                "on lc.owner = u " +
                "where u.email = :email " +
                "and lc.id = :id";
        return em.createQuery(jpql, LedgerCategory.class)
                .setParameter("email", email)
                .setParameter("id", id)
                .getResultList().stream().findFirst();
    }
}
