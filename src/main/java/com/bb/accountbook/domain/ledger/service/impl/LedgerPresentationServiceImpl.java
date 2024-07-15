package com.bb.accountbook.domain.ledger.service.impl;

import com.bb.accountbook.common.log.target.ExecutionTimeLog;
import com.bb.accountbook.domain.custom.service.CustomService;
import com.bb.accountbook.domain.ledger.dto.LedgerCategoryDto;
import com.bb.accountbook.domain.ledger.dto.LedgerDto;
import com.bb.accountbook.domain.ledger.dto.MonthlyLedgerResponseDto;
import com.bb.accountbook.domain.ledger.service.LedgerPresentationService;
import com.bb.accountbook.entity.Custom;
import com.bb.accountbook.entity.Ledger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LedgerPresentationServiceImpl implements LedgerPresentationService {

    private final CustomService customService;
    @Override
    @ExecutionTimeLog
    public MonthlyLedgerResponseDto getMonthlyLedgerResponseDto(List<Ledger> monthlyLedgers, String yearMonth) {
        MonthlyLedgerResponseDto dataDto = new MonthlyLedgerResponseDto(yearMonth);

        Map<Long, String> userColorMap = new HashMap<>();
        monthlyLedgers.parallelStream()
                .forEach(ledger -> {
                    switch (ledger.getCode()) {
                        case I -> dataDto.addTotalIncome(ledger.getAmount());
                        case E -> dataDto.addTotalExpenditure(ledger.getAmount());
                        case S -> dataDto.addTotalSave(ledger.getAmount());
                        default -> {
                        }
                    }

                    String color;

                    if (userColorMap.containsKey(ledger.getOwner().getId())) {
                        color = userColorMap.get(ledger.getOwner().getId());
                    }
                    else {
                        color = customService.getCustomColor(ledger.getOwner().getEmail());
                        userColorMap.put(ledger.getOwner().getId(), color);
                    }

                    LedgerDto ledgerDto = LedgerDto.builder()
                            .ledgerId(ledger.getId())
                            .ledgerCode(ledger.getCode())
                            .year(ledger.getDate().getYear())
                            .month(ledger.getDate().getMonthValue())
                            .day(ledger.getDate().getDayOfMonth())
                            .dayOfWeek(ledger.getDate().getDayOfWeek().getValue())
                            .description(ledger.getDescription())
                            .amount(ledger.getAmount())
                            .category(new LedgerCategoryDto(ledger.getLedgerCategory()))
                            .color(color)
                            .build();


                    dataDto.getLedgersPerDay().get(ledgerDto.getDay()).addLedger(ledgerDto);
                });


        return dataDto;
    }
}
