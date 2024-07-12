package com.bb.accountbook.domain.ledger.service;

import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.entity.LedgerCategory;

import java.util.List;

/**
 * 가계부 카테고리 서비스
 */
public interface LedgerCategoryService {

    /**
     * 가계부 카테고리 생성
     * @param email
     * @param name
     * @param ledgerCode
     * @param iconId
     * @return
     */
    Long insertLedgerCategory(String email, String name, LedgerCode ledgerCode, Long iconId);

    /**
     * 가계부 카테고리 단건 조회
     * @param id
     * @return
     */
    LedgerCategory findLedgerCategoryById(Long id);

    /**
     * 호출자 가계부 카테고리 단건 조회
     * @param email
     * @param id
     * @return
     */
    LedgerCategory findOwnLedgerCategory(String email, Long id);

    /**
     * 호출자 가계부 카테고리 전체 조회
     * @param email
     * @return
     */
    List<LedgerCategory> findOwnLedgerCategories(String email);

    /**
     * 가계부 카테고리 단건 제거
     * @param id
     */
    void deleteLedgerCategory(Long id);

    /**
     * 호출자 가계부 카테고리 단건 제거
     * @param email
     * @param categoryId
     */
    void deleteOwnLedgerCategory(String email, Long categoryId);

    /**
     * 호출자 가계부 카테고리 수정
     * @param email
     * @param categoryId
     * @param name
     * @param ledgerCode
     * @param iconId
     */
    void updateOwnLedgerCategory(String email, Long categoryId, String name, LedgerCode ledgerCode, Long iconId);

    /**
     * 가계부 카테고리 수정
     * @param categoryId
     * @param name
     * @param ledgerCode
     * @param iconId
     */
    void updateLedgerCategory(Long categoryId, String name, LedgerCode ledgerCode, Long iconId);

    /**
     * 가계부 카테고리 다건 생성 - 테스트용 메소드
     * @param ledgerCategories
     * @return
     */
    int insertLedgerCategoryList(List<LedgerCategory> ledgerCategories);

}
