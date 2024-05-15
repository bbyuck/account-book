package com.bb.accountbook.domain.ledger.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.common.model.status.CoupleStatus;
import com.bb.accountbook.common.util.DateTimeUtil;
import com.bb.accountbook.domain.couple.service.CoupleService;
import com.bb.accountbook.domain.ledger.dto.CoupleMonthlyLedgerDto;
import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerDto;
import com.bb.accountbook.domain.ledger.repository.LedgerRepository;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.entity.Couple;
import com.bb.accountbook.entity.Ledger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.bb.accountbook.common.model.codes.ErrorCode.ERR_LED_000;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LedgerService {

    private final LedgerRepository ledgerRepository;

    private final UserService userService;

    private final CoupleService coupleService;

    public Long insertLedger(Long apiCallerId, LedgerCode ledgerCode, LocalDate ledgerDate, Long ledgerAmount, String ledgerDescription) {
        Ledger savedLedger = ledgerRepository.save(
                new Ledger(userService.findUserById(apiCallerId), ledgerCode, ledgerDate, ledgerAmount, ledgerDescription)
        );
        return savedLedger.getId();
    }

    @Transactional(readOnly = true)
    public Ledger findLedger(Long ledgerId) {
        return ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> {
                    log.error(ERR_LED_000.getValue());
                    return new GlobalException(ERR_LED_000);
                });
    }

    public Long updateLedger(Long ledgerId, LedgerCode ledgerCode, LocalDate ledgerDate, Long ledgerAmount, String ledgerDescription) {
        Ledger targetLedger = findLedger(ledgerId);
        targetLedger.update(ledgerCode, ledgerDate, ledgerAmount, ledgerDescription);

        return targetLedger.getId();
    }

    /**
     * yearMonth -> yyyyMM
     * @param userId
     * @param yearMonth
     * @return
     */
    @Transactional(readOnly = true)
    public List<Ledger> findCoupleMonthlyLedger(Long userId, String yearMonth) {

        LocalDate[] monthlyDuration = DateTimeUtil.getMonthlyDuration(yearMonth);

        Couple couple = coupleService.findCoupleByUserId(userId);
        if (couple.getStatus() != CoupleStatus.ACTIVE) {
            log.error(ErrorCode.ERR_CPL_004.getValue());
            throw new GlobalException(ErrorCode.ERR_CPL_004);
        }

        return ledgerRepository.findCoupleMonthlyLedger(couple.getId(), monthlyDuration[0], monthlyDuration[1]);
    }

    /**
     * yearMonth -> yyyyMM
     * @param userId
     * @param yearMonth
     * @return
     */
    @Transactional(readOnly = true)
    public List<Ledger> findPersonalMonthlyLedger(Long userId, String yearMonth) {
        LocalDate[] monthlyDuration = DateTimeUtil.getMonthlyDuration(yearMonth);

        return ledgerRepository.findPersonalMonthlyLedger(userId, monthlyDuration[0], monthlyDuration[1]);
    }
}
