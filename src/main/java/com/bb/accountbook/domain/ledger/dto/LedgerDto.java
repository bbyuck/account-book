package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import com.bb.accountbook.entity.Ledger;
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

}
