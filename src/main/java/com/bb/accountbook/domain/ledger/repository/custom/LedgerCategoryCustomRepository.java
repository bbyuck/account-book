package com.bb.accountbook.domain.ledger.repository.custom;

import com.bb.accountbook.entity.LedgerCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerCategoryCustomRepository {
    List<LedgerCategory> findByOwnerEmail(String email);
}
