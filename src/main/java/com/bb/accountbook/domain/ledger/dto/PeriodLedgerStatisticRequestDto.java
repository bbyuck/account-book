package com.bb.accountbook.domain.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeriodLedgerStatisticRequestDto {
    private String email;

    private int startYear;
    private int startMonth;
    private int endYear;
    private int endMonth;
}
