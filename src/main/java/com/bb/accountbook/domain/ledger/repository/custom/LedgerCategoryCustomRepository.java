package com.bb.accountbook.domain.ledger.repository.custom;

import com.bb.accountbook.entity.LedgerCategory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LedgerCategoryCustomRepository {
    List<LedgerCategory> findByOwnerEmail(String email);

    List<LedgerCategory> findCoupleOwnCategories(Long coupleId);

    Optional<LedgerCategory> findCoupleOwnCategory(Long coupleId, Long id);

    Optional<LedgerCategory> findByOwnerEmailAndId(String email, Long id);
}
