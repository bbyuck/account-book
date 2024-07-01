package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LedgerDto {
    private Long ledgerId;
    private String ownerNickname;
    private LedgerCode ledgerCode;
    private Long amount;
    private String description;
    private String color;
    private LedgerCategoryDto category;

    private int year;
    private int month;
    private int day;
    private int dayOfWeek;

}
