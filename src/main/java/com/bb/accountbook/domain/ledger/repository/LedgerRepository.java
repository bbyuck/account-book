package com.bb.accountbook.domain.ledger.repository;

import com.bb.accountbook.domain.ledger.repository.custom.LedgerCustomRepository;
import com.bb.accountbook.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long>, LedgerCustomRepository {
}
