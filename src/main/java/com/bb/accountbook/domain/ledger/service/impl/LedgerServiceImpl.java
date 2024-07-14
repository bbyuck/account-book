package com.bb.accountbook.domain.ledger.service.impl;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.common.util.DateTimeUtil;
import com.bb.accountbook.domain.couple.service.CoupleService;
import com.bb.accountbook.domain.ledger.dto.AssetDto;
import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerRequestDto;
import com.bb.accountbook.domain.ledger.repository.LedgerRepository;
import com.bb.accountbook.domain.ledger.service.LedgerCategoryService;
import com.bb.accountbook.domain.ledger.service.LedgerService;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.entity.Couple;
import com.bb.accountbook.entity.Ledger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.bb.accountbook.common.model.codes.ErrorCode.ERR_CPL_003;
import static com.bb.accountbook.common.model.codes.ErrorCode.ERR_LED_000;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LedgerServiceImpl implements LedgerService {

    private final LedgerRepository ledgerRepository;

    private final UserService userService;

    private final CoupleService coupleService;

    private final LedgerCategoryService ledgerCategoryService;

    @Override
    public Long insertLedger(String apiCallerEmail, LedgerCode ledgerCode, LocalDate ledgerDate, Long ledgerAmount, String ledgerDescription) {
        return insertLedger(null, apiCallerEmail, ledgerCode, ledgerDate, ledgerAmount, ledgerDescription);
    }

    @Override
    public Long insertLedger(Long ledgerCategoryId, String apiCallerEmail, LedgerCode ledgerCode, LocalDate ledgerDate, Long ledgerAmount, String ledgerDescription) {
        if (ledgerAmount <= 0) {
            log.error(ErrorCode.ERR_LED_001.getValue());
            throw new GlobalException(ErrorCode.ERR_LED_001);
        }

        Ledger savedLedger = ledgerRepository.save(
                new Ledger(
                        ledgerCategoryId == null ? null : ledgerCategoryService.findOwnLedgerCategory(apiCallerEmail, ledgerCategoryId),
                        userService.findUserByEmail(apiCallerEmail),
                        ledgerCode,
                        ledgerDate,
                        ledgerAmount,
                        ledgerDescription)
        );
        return savedLedger.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Ledger findLedgerById(Long ledgerId) {
        return ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> {
                    log.error(ERR_LED_000.getValue());
                    return new GlobalException(ERR_LED_000);
                });
    }

    @Override
    public Long updateLedger(String email, Long ledgerId, LedgerCode ledgerCode, LocalDate ledgerDate, Long ledgerAmount, String ledgerDescription, Long ledgerCategoryId) {
        Ledger targetLedger = findLedger(email, ledgerId);
        targetLedger.update(ledgerCode, ledgerDate, ledgerAmount, ledgerDescription,
                ledgerCategoryId == null ? null : ledgerCategoryService.findOwnLedgerCategory(email, ledgerCategoryId));

        return targetLedger.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ledger> findLedgers(String userEmail) {
        return coupleService.isActiveCouple(userEmail) ? findCoupleLedgers(userEmail) : findPersonalLedgers(userEmail);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ledger> findPersonalLedgers(String email) {
        return ledgerRepository.findPersonalLedgers(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ledger> findCoupleLedgers(String email) {
        if (!coupleService.isActiveCouple(email)) {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "findPersonalLedgers", email, ERR_CPL_003.getValue());
            throw new GlobalException(ERR_CPL_003);
        }

        Couple couple = coupleService.findCoupleByUserEmail(email);

        return ledgerRepository.findCoupleLedgers(couple.getId());
    }


    @Override
    public Ledger findLedger(String email, Long ledgerId) {
        return coupleService.isActiveCouple(email)
                ? findCoupleLedger(coupleService.findCoupleByUserEmail(email).getId(), ledgerId)
                : findPersonalLedger(email, ledgerId);
    }


    @Override
    @Transactional(readOnly = true)
    public Ledger findPersonalLedger(String email, Long ledgerId) {
        return ledgerRepository.findLedgerByIdAndUserEmail(ledgerId, email).orElseThrow(() -> {
            log.debug("{}.{}({}, {}): {}", this.getClass().getName(), "findPersonalLedger", email, ledgerId, ERR_LED_000.getValue());
            return new GlobalException(ERR_LED_000);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Ledger findCoupleLedger(Long coupleId, Long ledgerId) {
        return ledgerRepository.findLedgerWithUserCouple(coupleId, ledgerId).orElseThrow(() -> {
            log.debug("{}.{}({}, {}): {}", this.getClass().getName(), "findCoupleLedger", coupleId, ledgerId, ERR_LED_000.getValue());
            return new GlobalException(ERR_LED_000);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public AssetDto findPersonalAsset(String userEmail) {
        List<Ledger> savings = ledgerRepository.findPersonalSavingsByEmail(userEmail);

        return new AssetDto(savings.stream()
                .mapToLong(Ledger::getAmount)
                .reduce(Long::sum).orElse(0L));
    }

    @Override
    @Transactional(readOnly = true)
    public AssetDto findCoupleAsset(String userEmail) {
        Couple couple = coupleService.findCoupleByUserEmail(userEmail);
        List<Ledger> savings = ledgerRepository.findCoupleSavings(couple.getId());

        return new AssetDto(savings.stream()
                .mapToLong(Ledger::getAmount)
                .reduce(Long::sum).orElse(0L));
    }

    @Override
    public boolean deleteLedger(String email, Long ledgerId) {
        Ledger ledger;
        if (coupleService.isActiveCouple(email)) {
            Long coupleId = coupleService.findCoupleByUserEmail(email).getId();
            ledger = ledgerRepository.findLedgerWithUserCouple(coupleId, ledgerId).orElseThrow(() -> {
                log.debug("{}.{}({}, {}): {}", this.getClass().getName(), "findCoupleLedger", coupleId, ledgerId, ERR_LED_000.getValue());
                return new GlobalException(ERR_LED_000);
            });
        } else {
            ledger = ledgerRepository.findLedgerByIdAndUserEmail(ledgerId, email).orElseThrow(() -> {
                log.debug("{}.{}({}, {}): {}", this.getClass().getName(), "findPersonalLedger", email, ledgerId, ERR_LED_000.getValue());
                return new GlobalException(ERR_LED_000);
            });
        }

        ledgerRepository.delete(ledger);

        return true;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Ledger> findCoupleMonthlyLedger(MonthlyLedgerRequestDto requestDto) {
        LocalDate[] monthlyDuration = DateTimeUtil.getMonthlyDuration(requestDto.getYearMonth());

        if (!coupleService.isActiveCouple(requestDto.getEmail())) {
            log.debug("{}.{}({}, {}): {}", this.getClass().getName(), "findCoupleMonthlyLedger", requestDto.getEmail(), requestDto.getYearMonth(), ERR_CPL_003.getValue());
            throw new GlobalException(ERR_CPL_003);
        }

        Couple couple = coupleService.findCoupleByUserEmail(requestDto.getEmail());

        return ledgerRepository.findCouplePeriodLedger(couple.getId(), monthlyDuration[0], monthlyDuration[1], requestDto.getLedgerCode());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ledger> findMonthlyLedger(MonthlyLedgerRequestDto requestDto) {
        return coupleService.isActiveCouple(requestDto.getEmail())
                ? findCoupleMonthlyLedger(requestDto)
                : findPersonalMonthlyLedger(requestDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ledger> findPersonalMonthlyLedger(MonthlyLedgerRequestDto requestDto) {
        LocalDate[] monthlyDuration = DateTimeUtil.getMonthlyDuration(requestDto.getYearMonth());
        return ledgerRepository.findPersonalPeriodLedgerByEmail(requestDto.getEmail(), monthlyDuration[0], monthlyDuration[1], requestDto.getLedgerCode());
    }
}
