package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.entity.Ledger;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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


    public LedgerDto(Ledger ledger) {
        this.ledgerId = ledger.getId();
        this.ledgerCode = ledger.getCode();
        this.year = ledger.getDate().getYear();
        this.month = ledger.getDate().getMonthValue();
        this.day = ledger.getDate().getDayOfMonth();
        this.dayOfWeek = ledger.getDate().getDayOfWeek().getValue();
        this.description = ledger.getDescription();
        this.amount = ledger.getAmount();
        if (ledger.getLedgerCategory() != null) {
            this.category = new LedgerCategoryDto(ledger.getLedgerCategory());
        }
    }

    @QueryProjection
    public LedgerDto(Long ledgerId, LedgerCode ledgerCode, Long amount, String description, String color, LedgerCategoryDto category, LocalDate date) {
        this.ledgerId = ledgerId;
        this.ledgerCode = ledgerCode;
        this.amount = amount;
        this.description = description;
        this.color = color;
        this.category = category;
        this.year = date.getYear();
        this.month = date.getMonthValue();
        this.day = date.getDayOfMonth();
        this.dayOfWeek = date.getDayOfWeek().getValue();
    }
}
