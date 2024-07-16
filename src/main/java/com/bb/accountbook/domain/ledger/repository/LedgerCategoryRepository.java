package com.bb.accountbook.domain.ledger.repository;

import com.bb.accountbook.domain.ledger.repository.custom.LedgerCategoryCustomRepository;
import com.bb.accountbook.entity.LedgerCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LedgerCategoryRepository extends JpaRepository<LedgerCategory, Long>, LedgerCategoryCustomRepository {

}
