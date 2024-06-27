package com.bb.accountbook.domain.ledger.repository.custom.impl;

import com.bb.accountbook.domain.ledger.repository.custom.LedgerCategoryCustomRepository;
import com.bb.accountbook.entity.LedgerCategory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
