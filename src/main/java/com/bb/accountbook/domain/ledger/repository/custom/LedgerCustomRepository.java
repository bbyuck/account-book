package com.bb.accountbook.domain.ledger.repository.custom;

import com.bb.accountbook.entity.Ledger;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LedgerCustomRepository {
    /**
     * 기간별 조회
     */
    List<Ledger> findPersonalPeriodLedger(Long userId, LocalDate startDate, LocalDate endDate);
    List<Ledger> findCouplePeriodLedger(Long coupleId, LocalDate startDate, LocalDate endDate);
    List<Ledger> findPersonalPeriodLedgerByEmail(String email, LocalDate startDate, LocalDate endDate);

    /**
     * 단건 조회
     */
    Optional<Ledger> findLedgerWithUserCouple(Long coupleId, Long ledgerId);
    Optional<Ledger> findLedgerByIdAndUserEmail(Long ledgerId, String email);

    /**
     * 저금액 조회
     */
    List<Ledger> findPersonalSavings(Long userId);
    List<Ledger> findPersonalSavingsByEmail(String email);
    List<Ledger> findCoupleSavings(Long coupleId);
}
