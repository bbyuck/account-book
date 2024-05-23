package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LedgerDto {
    private Long ledgerId;
    private String ownerNickname;
    private LedgerCode ledgerCode;
    private String ledgerCodeValue;
    private int day;
    private Long amount;
    private String description;

    public LedgerDto(Long ledgerId, String nickname, LedgerCode code, LocalDate date, Long amount, String description) {
        this.ledgerId = ledgerId;
        this.ownerNickname = nickname;
        this.ledgerCode = code;
        this.ledgerCodeValue = code.getValue();
        this.day = date.getDayOfMonth();
        this.amount = amount;
        this.description = description;
    }
}
