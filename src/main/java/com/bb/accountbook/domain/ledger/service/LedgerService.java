package com.bb.accountbook.domain.ledger.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.domain.ledger.dto.LedgerInsertRequestDto;
import com.bb.accountbook.domain.ledger.dto.LedgerUpdateRequestDto;
import com.bb.accountbook.domain.ledger.repository.LedgerRepository;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.entity.Ledger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bb.accountbook.common.model.codes.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LedgerService {

    private final LedgerRepository ledgerRepository;

    private final UserService userService;

    public Long insertLedger(Long apiCallerId, LedgerInsertRequestDto requestDto) {
        Ledger savedLedger = ledgerRepository.save(
                new Ledger(
                        userService.findUserById(apiCallerId),
                        requestDto.getLedgerCode(),
                        requestDto.getLedgerDate(),
                        requestDto.getLedgerAmount(),
                        requestDto.getLedgerDescription()
                )
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

    public Long updateLedger(Long ledgerId, LedgerUpdateRequestDto requestDto) {
        Ledger targetLedger = findLedger(ledgerId);
        targetLedger.update(requestDto.getLedgerCode(), requestDto.getLedgerDate(), requestDto.getLedgerAmount(), requestDto.getLedgerDescription());

        return targetLedger.getId();
    }
}
