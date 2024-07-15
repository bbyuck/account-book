package com.bb.accountbook.domain.ledger.service.impl;

import com.bb.accountbook.common.log.target.ExecutionTimeLog;
import com.bb.accountbook.domain.custom.service.CustomService;
import com.bb.accountbook.domain.ledger.dto.LedgerCategoryDto;
import com.bb.accountbook.domain.ledger.dto.LedgerDto;
import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerResponseDto;
import com.bb.accountbook.domain.ledger.service.LedgerPresentationService;
import com.bb.accountbook.entity.Ledger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LedgerPresentationServiceImpl implements LedgerPresentationService {

    private final CustomService customService;
    @Override
    @ExecutionTimeLog
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
}
