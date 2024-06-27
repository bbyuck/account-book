package com.bb.accountbook.domain.ledger.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.domain.ledger.repository.LedgerCategoryRepository;
import com.bb.accountbook.domain.ledger.repository.LedgerRepository;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.entity.LedgerCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LedgerCategoryService {

    private final LedgerCategoryRepository ledgerCategoryRepository;

    private final LedgerRepository ledgerRepository;

    private final UserService userService;

    public Long insertLedgerCategory(String email, String name, LedgerCode ledgerCode) {
        LedgerCategory category = new LedgerCategory(userService.findUserByEmail(email), name, ledgerCode);
        return ledgerCategoryRepository.save(category).getId();
    }

    @Transactional(readOnly = true)
    public LedgerCategory findLedgerCategoryById(Long id) {
        return ledgerCategoryRepository.findById(id).orElseThrow(() -> {
            log.debug("{} ====== {}", ErrorCode.ERR_LED_002.getValue(), id);
            return new GlobalException(ErrorCode.ERR_LED_002);
        });
    }

    @Transactional(readOnly = true)
    public List<LedgerCategory> findOwnLedgerCategories(String email) {
        return ledgerCategoryRepository.findByOwnerEmail(email);
    }

    public void deleteLedgerCategory(Long id) {
        ledgerRepository.clearLedgerCategories(id);
        ledgerCategoryRepository.delete(findLedgerCategoryById(id));
    }
}
