package com.bb.accountbook.domain.ledger.repository.custom;

import com.bb.accountbook.entity.Ledger;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LedgerCustomRepository {

    List<Ledger> findPersonalMonthlyLedger(Long userId, LocalDate startDate, LocalDate endDate);
    List<Ledger> findCoupleMonthlyLedger(Long coupleId, LocalDate startDate, LocalDate endDate);
}
