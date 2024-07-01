package com.bb.accountbook.domain.ledger.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.common.util.DateTimeUtil;
import com.bb.accountbook.domain.couple.service.CoupleService;
import com.bb.accountbook.domain.custom.service.CustomService;
import com.bb.accountbook.domain.ledger.dto.*;
import com.bb.accountbook.domain.ledger.repository.LedgerRepository;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.entity.Couple;
import com.bb.accountbook.entity.Ledger;
import com.bb.accountbook.entity.LedgerCategory;
import com.bb.accountbook.entity.User;
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
public class LedgerService {

    private final LedgerRepository ledgerRepository;

    private final UserService userService;

    private final CoupleService coupleService;

    private final CustomService customService;

    private final LedgerCategoryService ledgerCategoryService;

    /**
     * 가계부 상세 항목입력
     *
     * @param apiCallerEmail
     * @param ledgerCode
     * @param ledgerDate
     * @param ledgerAmount
     * @param ledgerDescription
     * @return
     */
    public Long insertLedger(String apiCallerEmail, LedgerCode ledgerCode, LocalDate ledgerDate, Long ledgerAmount, String ledgerDescription) {
        return insertLedger(null, apiCallerEmail, ledgerCode, ledgerDate, ledgerAmount, ledgerDescription);
    }

    /**
     * 가계부 상세 항목입력
     *
     * @param ledgerCategoryId
     * @param apiCallerEmail
     * @param ledgerCode
     * @param ledgerDate
     * @param ledgerAmount
     * @param ledgerDescription
     * @return
     */
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

    /**
     * 가계부 상세 항목 ID로 가계부 상세 항목 조회
     *
     * @param ledgerId
     * @return
     */
    @Transactional(readOnly = true)
    public Ledger findLedgerById(Long ledgerId) {
        return ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> {
                    log.error(ERR_LED_000.getValue());
                    return new GlobalException(ERR_LED_000);
                });
    }

    /**
     * 가계부 상세 항목 수정
     *
     * @param ledgerId
     * @param ledgerCode
     * @param ledgerDate
     * @param ledgerAmount
     * @param ledgerDescription
     * @param ledgerCategoryId
     * @return
     */
    public Long updateLedger(String email, Long ledgerId, LedgerCode ledgerCode, LocalDate ledgerDate, Long ledgerAmount, String ledgerDescription, Long ledgerCategoryId) {
        Ledger targetLedger = findLedger(email, ledgerId);
        targetLedger.update(ledgerCode, ledgerDate, ledgerAmount, ledgerDescription,
                ledgerCategoryId == null ? null : ledgerCategoryService.findOwnLedgerCategory(email, ledgerCategoryId));

        return targetLedger.getId();
    }

    /**
     * 커플 월별 가계부 상세 항목 목록 조회
     * yearMonth -> yyyyMM
     *
     * @param email
     * @param yearMonth
     * @return
     */
    @Transactional(readOnly = true)
    public List<Ledger> findCoupleMonthlyLedger(String email, String yearMonth) {
        LocalDate[] monthlyDuration = DateTimeUtil.getMonthlyDuration(yearMonth);

        if (!coupleService.isActiveCouple(email)) {
            log.debug("{}.{}({}, {}): {}", this.getClass().getName(), "findCoupleMonthlyLedger", email, yearMonth, ERR_CPL_003.getValue());
            throw new GlobalException(ERR_CPL_003);
        }

        Couple couple = coupleService.findCoupleByUserEmail(email);

        return ledgerRepository.findCouplePeriodLedger(couple.getId(), monthlyDuration[0], monthlyDuration[1]);
    }

    /**
     * 개인 월별 가계부 상세 항목 목록 조회
     * yearMonth -> yyyyMM
     *
     * @param userEmail
     * @param yearMonth
     * @return
     */
    @Transactional(readOnly = true)
    public List<Ledger> findPersonalMonthlyLedger(String userEmail, String yearMonth) {
        LocalDate[] monthlyDuration = DateTimeUtil.getMonthlyDuration(yearMonth);
        return ledgerRepository.findPersonalPeriodLedgerByEmail(userEmail, monthlyDuration[0], monthlyDuration[1]);
    }

    @Transactional(readOnly = true)
    public List<Ledger> findMonthlyLedger(String userEmail, String yearMonth) {
        return coupleService.isActiveCouple(userEmail) ? findCoupleMonthlyLedger(userEmail, yearMonth) : findPersonalMonthlyLedger(userEmail, yearMonth);
    }

    @Transactional(readOnly = true)
    public List<Ledger> findLedgers(String userEmail) {
        return coupleService.isActiveCouple(userEmail) ? findCoupleLedgers(userEmail) : findPersonalLedgers(userEmail);
    }

    @Transactional(readOnly = true)
    public List<Ledger> findPersonalLedgers(String email) {
        return ledgerRepository.findPersonalLedgers(email);
    }

    @Transactional(readOnly = true)
    public List<Ledger> findCoupleLedgers(String email) {
        if (!coupleService.isActiveCouple(email)) {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "findPersonalLedgers", email, ERR_CPL_003.getValue());
            throw new GlobalException(ERR_CPL_003);
        }

        Couple couple = coupleService.findCoupleByUserEmail(email);

        return ledgerRepository.findCoupleLedgers(couple.getId());
    }


    /**
     * 가계부 상세 항목 조회
     *
     * @param email
     * @param ledgerId
     * @return
     */
    public Ledger findLedger(String email, Long ledgerId) {
        return coupleService.isActiveCouple(email)
                ? findCoupleLedger(coupleService.findCoupleByUserEmail(email).getId(), ledgerId)
                : findPersonalLedger(email, ledgerId);
    }


    /**
     * 개인 가계부 상세 항목 조회
     *
     * @param ledgerId
     * @return
     */
    @Transactional(readOnly = true)
    public Ledger findPersonalLedger(String email, Long ledgerId) {
        return ledgerRepository.findLedgerByIdAndUserEmail(ledgerId, email).orElseThrow(() -> {
            log.debug("{}.{}({}, {}): {}", this.getClass().getName(), "findPersonalLedger", email, ledgerId, ERR_LED_000.getValue());
            return new GlobalException(ERR_LED_000);
        });
    }

    /**
     * 커플 가계부 상세 항목 조회
     *
     * @param coupleId
     * @param ledgerId
     * @return
     */
    @Transactional(readOnly = true)
    public Ledger findCoupleLedger(Long coupleId, Long ledgerId) {
        return ledgerRepository.findLedgerWithUserCouple(coupleId, ledgerId).orElseThrow(() -> {
            log.debug("{}.{}({}, {}): {}", this.getClass().getName(), "findCoupleLedger", coupleId, ledgerId, ERR_LED_000.getValue());
            return new GlobalException(ERR_LED_000);
        });
    }

    public MonthlyLedgerResponseDto getMonthlyLedgerResponseDto(List<Ledger> monthlyLedgers, String yearMonth) {
        MonthlyLedgerResponseDto dataDto = new MonthlyLedgerResponseDto(yearMonth);

        monthlyLedgers
                .forEach(ledger -> {
                    switch (ledger.getCode()) {
                        case I -> dataDto.addTotalIncome(ledger.getAmount());
                        case E -> dataDto.addTotalExpenditure(ledger.getAmount());
                        case S -> dataDto.addTotalSave(ledger.getAmount());
                        default -> {
                        }
                    }
                    LedgerDto ledgerDto = LedgerDto.builder()
                            .ledgerId(ledger.getId())
//                            .ownerNickname(ledger.getOwner().getUserCouple() != null ? ledger.getOwner().getUserCouple().getNickname() : "")
                            .ledgerCode(ledger.getCode())
                            .year(ledger.getDate().getYear())
                            .month(ledger.getDate().getMonthValue())
                            .day(ledger.getDate().getDayOfMonth())
                            .dayOfWeek(ledger.getDate().getDayOfWeek().getValue())
                            .description(ledger.getDescription())
                            .amount(ledger.getAmount())
                            .category(new LedgerCategoryDto(ledger.getLedgerCategory()))
                            .color(customService.getCustomColor(ledger.getOwner().getEmail()))
                            .build();


                    dataDto.getLedgersPerDay().get(ledgerDto.getDay()).addLedger(ledgerDto);
                });


        return dataDto;
    }

    @Transactional(readOnly = true)
    public AssetDto findPersonalAsset(String userEmail) {
        List<Ledger> savings = ledgerRepository.findPersonalSavingsByEmail(userEmail);

        return new AssetDto(savings.stream()
                .mapToLong(Ledger::getAmount)
                .reduce(Long::sum).orElse(0L));
    }

    @Transactional(readOnly = true)
    public AssetDto findCoupleAsset(String userEmail) {
        Couple couple = coupleService.findCoupleByUserEmail(userEmail);
        List<Ledger> savings = ledgerRepository.findCoupleSavings(couple.getId());

        return new AssetDto(savings.stream()
                .mapToLong(Ledger::getAmount)
                .reduce(Long::sum).orElse(0L));
    }

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
}
