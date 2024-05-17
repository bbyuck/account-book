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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.bb.accountbook.common.model.codes.ErrorCode.ERR_LED_000;

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
    public Long insertLedger(Long apiCallerId, LedgerCode ledgerCode, LocalDate ledgerDate, Long ledgerAmount, String ledgerDescription) {
        Ledger savedLedger = ledgerRepository.save(
                new Ledger(userService.findUserById(apiCallerId), ledgerCode, ledgerDate, ledgerAmount, ledgerDescription)
        );
        return savedLedger.getId();
    }

    /**
     * 가계부 상세 항목 ID로 가계부 상세 항목 조회
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
     * 개인 월별 가계부 상세 항목 목록 조회
     * yearMonth -> yyyyMM
     *
     * @param userId
     * @param yearMonth
     * @return
     */
    @Transactional(readOnly = true)
    public List<Ledger> findPersonalMonthlyLedger(Long userId, String yearMonth) {
        LocalDate[] monthlyDuration = DateTimeUtil.getMonthlyDuration(yearMonth);
        return ledgerRepository.findPersonalMonthlyLedger(userId, monthlyDuration[0], monthlyDuration[1]);
    }


    /**
     * 개인 가계부 상세 항목 조회
     * @param ledgerId
     * @return
     */
    @Transactional(readOnly = true)
    public LedgerPersonalDetailDto findPersonalLedger(Long ledgerId) {
        Ledger ledger = findLedgerById(ledgerId);
        return new LedgerPersonalDetailDto(ledger.getId(), ledger.getCode(), ledger.getDate(), ledger.getAmount(), ledger.getDescription());
    }

    /**
     * 커플 가계부 상세 항목 조회
     * @param coupleId
     * @param ledgerId
     * @return
     */
    @Transactional(readOnly = true)
    public LedgerCoupleDetailDto findCoupleLedger(Long coupleId, Long ledgerId) {
        Ledger ledger = ledgerRepository.findLedgerWithUserCouple(coupleId, ledgerId).orElseThrow(() -> {
            log.error(ERR_LED_000.getValue());
            log.error("{}, {}",coupleId, ledgerId);
            return new GlobalException(ERR_LED_000);
        });

        return new LedgerCoupleDetailDto(ledger.getId(), ledger.getCode(), ledger.getDate(), ledger.getAmount(), ledger.getDescription(), ledger.getOwner().getUserCouple().getNickname());
    }

    public MonthlyLedgerResponseDto getMonthlyLedgerResponseDto(List<Ledger> monthlyLedgers, String yearMonth) {
        MonthlyLedgerResponseDto dataDto = new MonthlyLedgerResponseDto();
        AtomicReference<Long> totalAmount = new AtomicReference<>(0L);

        List<MonthlyLedgerDto> monthlyLedgerDtos = monthlyLedgers.stream()
                .map(ledger -> {
                    switch (ledger.getCode()) {
                        case I:
                            totalAmount.updateAndGet(v -> v + ledger.getAmount());
                            break;
                        case E:
                        case S:
                            totalAmount.updateAndGet(v -> v - ledger.getAmount());
                            break;
                        default:
                            break;
                    }

                    return new CoupleMonthlyLedgerDto(
                            ledger.getCode().getValue(),
                            ledger.getDate(),
                            ledger.getAmount(),
                            ledger.getDescription(),
                            ledger.getOwner().getUserCouple().getNickname());
                })
                .collect(Collectors.toList());

        dataDto.setLedgers(monthlyLedgerDtos);
        dataDto.setTotalAmount(totalAmount.get());
        dataDto.setYearMonth(yearMonth);

        return dataDto;
    }

    public AssetDto findPersonalAsset(Long currentUserId) {
        List<Ledger> savings = ledgerRepository.findPersonalSavings(currentUserId);

        return new AssetDto(savings.stream()
                .mapToLong(Ledger::getAmount)
                .reduce((a, b) -> a + b).getAsLong());
    }
}
