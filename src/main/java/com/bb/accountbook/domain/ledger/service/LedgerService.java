package com.bb.accountbook.domain.ledger.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.common.model.status.CoupleStatus;
import com.bb.accountbook.common.util.DateTimeUtil;
import com.bb.accountbook.domain.couple.service.CoupleService;
import com.bb.accountbook.domain.ledger.dto.*;
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
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.bb.accountbook.common.model.codes.ErrorCode.*;
import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LedgerService {

    private final LedgerRepository ledgerRepository;

    private final UserService userService;

    private final CoupleService coupleService;

    /**
     * 가계부 상세 항목입력
     *
     * @param apiCallerId
     * @param ledgerCode
     * @param ledgerDate
     * @param ledgerAmount
     * @param ledgerDescription
     * @return
     */
    public Long insertLedger(String apiCallerEmail, LedgerCode ledgerCode, LocalDate ledgerDate, Long ledgerAmount, String ledgerDescription) {
        if (ledgerAmount <= 0) {
            log.error(ErrorCode.ERR_LED_001.getValue());
            throw new GlobalException(ErrorCode.ERR_LED_001);
        }

        Ledger savedLedger = ledgerRepository.save(
                new Ledger(userService.findUserByEmail(apiCallerEmail), ledgerCode, ledgerDate, ledgerAmount, ledgerDescription)
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
     * @return
     */
    public Long updateLedger(Long ledgerId, LedgerCode ledgerCode, LocalDate ledgerDate, Long ledgerAmount, String ledgerDescription) {
        Ledger targetLedger = findLedgerById(ledgerId);
        targetLedger.update(ledgerCode, ledgerDate, ledgerAmount, ledgerDescription);

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

        if (!coupleService.isExistCouple(email)) {
            log.debug("{}.{}({}, {}): {}", this.getClass().getName(), "findCoupleMonthlyLedger", email, yearMonth, ERR_CPL_003.getValue());
            throw new GlobalException(ERR_CPL_003);
        }

        Couple couple = coupleService.findCoupleByUserEmail(email);

        return ledgerRepository.findCoupleMonthlyLedger(couple.getId(), monthlyDuration[0], monthlyDuration[1]);
    }

    /**
     * 개인 월별 가계부 상세 항목 목록 조회
     * yearMonth -> yyyyMM
     *
     * @param userId
     * @param yearMonth
     * @return
     */
    @Transactional(readOnly = true)
    public List<Ledger> findPersonalMonthlyLedger(String userEmail, String yearMonth) {
        LocalDate[] monthlyDuration = DateTimeUtil.getMonthlyDuration(yearMonth);
        return ledgerRepository.findPersonalMonthlyLedgerByEmail(userEmail, monthlyDuration[0], monthlyDuration[1]);
    }

    @Transactional(readOnly = true)
    public List<Ledger> findMonthlyLedger(String userEmail, String yearMonth) {
        return coupleService.isExistCouple(userEmail) ? findCoupleMonthlyLedger(userEmail, yearMonth) : findPersonalMonthlyLedger(userEmail, yearMonth);
    }


    /**
     * 가계부 상세 항목 조회
     *
     * @param ledgerId
     * @return
     */
    public LedgerDetailDto findLedger(String email, Long ledgerId) {
        return coupleService.isCouple(email)
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
    public LedgerPersonalDetailDto findPersonalLedger(String email, Long ledgerId) {
        Ledger ledger = ledgerRepository.findLedgerByIdAndUserEmail(ledgerId, email).orElseThrow(() -> {
            log.debug("{}.{}({}, {}): {}", this.getClass().getName(), "findPersonalLedger", email, ledgerId, ERR_LED_000.getValue());
            return new GlobalException(ERR_LED_000);
        });
        return new LedgerPersonalDetailDto(ledger.getId(), ledger.getCode(), ledger.getDate(), ledger.getAmount(), ledger.getDescription());
    }

    /**
     * 커플 가계부 상세 항목 조회
     *
     * @param coupleId
     * @param ledgerId
     * @return
     */
    @Transactional(readOnly = true)
    public LedgerCoupleDetailDto findCoupleLedger(Long coupleId, Long ledgerId) {
        Ledger ledger = ledgerRepository.findLedgerWithUserCouple(coupleId, ledgerId).orElseThrow(() -> {
            log.debug("{}.{}({}, {}): {}", this.getClass().getName(), "findCoupleLedger", coupleId, ledgerId, ERR_LED_000.getValue());
            return new GlobalException(ERR_LED_000);
        });

        return new LedgerCoupleDetailDto(ledger.getId(), ledger.getCode(), ledger.getDate(), ledger.getAmount(), ledger.getDescription(), ledger.getOwner().getUserCouple().getNickname());
    }

    public MonthlyLedgerResponseDto getMonthlyLedgerResponseDto(List<Ledger> monthlyLedgers, String yearMonth) {
        MonthlyLedgerResponseDto dataDto = new MonthlyLedgerResponseDto();

        dataDto.setYearMonth(yearMonth);
        AtomicReference<Long> totalIncome = new AtomicReference<>(0L);
        AtomicReference<Long> totalExpenditure = new AtomicReference<>(0L);
        AtomicReference<Long> totalSave = new AtomicReference<>(0L);


        Map<Integer, DailyLedgerDto> ledgersPerDay = monthlyLedgers.stream()
                .map(ledger -> {
                    switch (ledger.getCode()) {
                        case I -> totalIncome.updateAndGet(v -> v + ledger.getAmount());
                        case E -> totalExpenditure.updateAndGet(v -> v + ledger.getAmount());
                        case S -> totalSave.updateAndGet(v -> v + ledger.getAmount());
                        default -> {
                        }
                    }

                    return new LedgerDto(
                            ledger.getId(),
                            ledger.getOwner().getUserCouple() != null ? ledger.getOwner().getUserCouple().getNickname() : "",
                            ledger.getCode(),
                            ledger.getDate(),
                            ledger.getAmount(),
                            ledger.getDescription()
                    );
                })
                .collect(groupingBy(LedgerDto::getDay))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> {
                            DailyLedgerDto dailyLedgerDto = new DailyLedgerDto();
                            dailyLedgerDto.setLedgers(e.getValue());
                            dailyLedgerDto.getLedgers().forEach(ledgerDto -> {
                                if (ledgerDto.getLedgerCode() == LedgerCode.I) {
                                    dailyLedgerDto.addDailyIncome(ledgerDto.getAmount());
                                } else if (ledgerDto.getLedgerCode() == LedgerCode.E || ledgerDto.getLedgerCode() == LedgerCode.S) {
                                    dailyLedgerDto.addDailyExpenditure(ledgerDto.getAmount());
                                }
                            });

                            return dailyLedgerDto;
                        }
                ));


        dataDto.setTotalIncome(totalIncome.get());
        dataDto.setTotalExpenditure(totalExpenditure.get());
        dataDto.setTotalSave(totalSave.get());
        dataDto.setLedgersPerDay(ledgersPerDay);

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

    public String deleteLedger(String email, Long ledgerId) {
        return null;
    }
}
