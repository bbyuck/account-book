package com.bb.accountbook.domain.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class MonthlyLedgerResponseDto {
    private String yearMonth;
    private List<MonthlyLedgerDto> ledgers;

}
