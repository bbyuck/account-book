package com.bb.accountbook.domain.ledger.repository.custom;

import com.bb.accountbook.entity.Ledger;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LedgerCustomRepository {

    List<Ledger> findPersonalMonthlyLedger(Long userId, LocalDate startDate, LocalDate endDate);
    List<Ledger> findCoupleMonthlyLedger(Long coupleId, LocalDate startDate, LocalDate endDate);
    Optional<Ledger> findLedgerWithUserCouple(Long coupleId, Long ledgerId);
    List<Ledger> findPersonalSavings(Long userId);
    List<Ledger> findPersonalSavingsByEmail(String email);
    List<Ledger> findCoupleSavings(Long coupleId);
    List<Ledger> findPersonalMonthlyLedgerByEmail(String email, LocalDate startDate, LocalDate endDate);
    Optional<Ledger> findLedgerByIdAndUserEmail(Long ledgerId, String email);
}
