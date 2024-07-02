package com.bb.accountbook.domain.ledger.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.domain.couple.service.CoupleService;
import com.bb.accountbook.domain.icon.service.IconService;
import com.bb.accountbook.domain.ledger.repository.LedgerCategoryRepository;
import com.bb.accountbook.domain.ledger.repository.LedgerRepository;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.entity.Couple;
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

    private final CoupleService coupleService;

    private final IconService iconService;

    public Long insertLedgerCategory(String email, String name, LedgerCode ledgerCode, Long iconId) {
        LedgerCategory category = new LedgerCategory(userService.findUserByEmail(email), name, ledgerCode, iconService.findIconById(iconId));
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
    public LedgerCategory findOwnLedgerCategory(String email, Long id) {
        if (coupleService.isActiveCouple(email)) {
            Couple couple = coupleService.findCoupleByUserEmail(email);
            return ledgerCategoryRepository.findCoupleOwnCategory(couple.getId(), id).orElseThrow(() -> {
                log.debug("{} ====== {}", ErrorCode.ERR_LED_002.getValue(), id);
                return new GlobalException(ErrorCode.ERR_LED_002);
            });
        }
        else {
            return ledgerCategoryRepository.findByOwnerEmailAndId(email, id).orElseThrow(() -> {
                log.debug("{} ====== {}", ErrorCode.ERR_LED_002.getValue(), id);
                return new GlobalException(ErrorCode.ERR_LED_002);
            });
        }
    }

    @Transactional(readOnly = true)
    public List<LedgerCategory> findOwnLedgerCategories(String email) {
        if (coupleService.isActiveCouple(email)) {
            Couple couple = coupleService.findCoupleByUserEmail(email);
            return ledgerCategoryRepository.findCoupleOwnCategories(couple.getId());
        }
        else {
            return ledgerCategoryRepository.findByOwnerEmail(email);
        }
    }

    public void deleteLedgerCategory(Long id) {
        ledgerRepository.clearLedgerCategories(id);
        ledgerCategoryRepository.deleteById(id);
    }

    public void deleteOwnLedgerCategory(String email, Long categoryId) {
        findOwnLedgerCategory(email, categoryId);
        deleteLedgerCategory(categoryId);
    }

    public void updateOwnLedgerCategory(String email, Long categoryId, String name, LedgerCode ledgerCode, Long iconId) {
        LedgerCategory category = findOwnLedgerCategory(email, categoryId);
        category.update(name, ledgerCode, iconService.findIconById(iconId));
    }

    public void updateLedgerCategory(Long categoryId, String name, LedgerCode ledgerCode, Long iconId) {
        LedgerCategory category = findLedgerCategoryById(categoryId);
        category.update(name, ledgerCode, iconService.findIconById(iconId));
    }

    public int insertLedgerCategoryList(List<LedgerCategory> ledgerCategories) {
        return ledgerCategoryRepository.saveAll(ledgerCategories).size();
    }
}
