package com.bb.accountbook.domain.ledger.service;

import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.domain.ledger.dto.AssetDto;
import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerRequestDto;
import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerResponseDto;
import com.bb.accountbook.entity.Ledger;

import java.time.LocalDate;
import java.util.List;


/**
 * 가계부 서비스
 */
public interface LedgerService {

    /**
     * 가계부 입력
     * @param apiCallerEmail
     * @param ledgerCode
     * @param ledgerDate
     * @param ledgerAmount
     * @param ledgerDescription
     * @return
     */
    Long insertLedger(String apiCallerEmail, LedgerCode ledgerCode, LocalDate ledgerDate, Long ledgerAmount, String ledgerDescription);

    /**
     * 가계부 입력
     * @param apiCallerEmail
     * @param ledgerCode
     * @param ledgerDate
     * @param ledgerAmount
     * @param ledgerDescription
     * @return
     */
    Long insertLedger(Long ledgerCategoryId, String apiCallerEmail, LedgerCode ledgerCode, LocalDate ledgerDate, Long ledgerAmount, String ledgerDescription);

    /**
     * 가계부 항목 ID로 가계부 상세 항목 조회
     *
     * @param ledgerId
     * @return
     */
    Ledger findLedgerById(Long ledgerId);

    /**
     * 가계부 항목 수정
     *
     * @param ledgerId
     * @param ledgerCode
     * @param ledgerDate
     * @param ledgerAmount
     * @param ledgerDescription
     * @param ledgerCategoryId
     * @return
     */
    Long updateLedger(String email, Long ledgerId, LedgerCode ledgerCode, LocalDate ledgerDate, Long ledgerAmount, String ledgerDescription, Long ledgerCategoryId);

    /**
     * 전체 가계부 조회
     * @param userEmail
     * @return
     */
    List<Ledger> findLedgers(String userEmail);

    /**
     * 개인 가계부 전체 조회
     * @param email
     * @return
     */
    List<Ledger> findPersonalLedgers(String email);

    /**
     * 커플 가계부 전체 조회
     * @param email
     * @return
     */
    List<Ledger> findCoupleLedgers(String email);


    /**
     * 가계부 상세 항목 단건 조회
     *
     * @param email
     * @param ledgerId
     * @return
     */
    Ledger findLedger(String email, Long ledgerId);


    /**
     * 개인 가계부 상세 항목 단건 조회
     *
     * @param ledgerId
     * @return
     */
    Ledger findPersonalLedger(String email, Long ledgerId);

    /**
     * 커플 가계부 상세 항목 단건 조회
     *
     * @param coupleId
     * @param ledgerId
     * @return
     */
    Ledger findCoupleLedger(Long coupleId, Long ledgerId);


    /**
     * 개인 자산 조회
     * @param userEmail
     * @return
     */
    AssetDto findPersonalAsset(String userEmail);

    /**
     * 커플 자산 조회
     * @param userEmail
     * @return
     */
    AssetDto findCoupleAsset(String userEmail);

    /**
     * 가계부 삭제 - hard delete version
     * @param email
     * @param ledgerId
     * @return
     */
    boolean deleteLedger(String email, Long ledgerId);

    /**
     * 월별 가계부 목록 조회
     * @param requestDto
     * @return
     */
    List<Ledger> findMonthlyLedger(MonthlyLedgerRequestDto requestDto);

    /**
     * 커플 월별 가계부 상세 항목 목록 조회
     * yearMonth -> yyyyMM
     *
     * @param requestDto
     * @return
     */
    List<Ledger> findCoupleMonthlyLedger(MonthlyLedgerRequestDto requestDto);

    /**
     * 개인 월별 가계부 상세 항목 목록 조회
     * yearMonth -> yyyyMM
     *
     * @param requestDto
     * @return
     */
    List<Ledger> findPersonalMonthlyLedger(MonthlyLedgerRequestDto requestDto);

}
