package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class LedgerCoupleDetailDto extends LedgerDetailDto {
    private String ownerNickname;

    public LedgerCoupleDetailDto(Long ledgerId, LedgerCode ledgerCode, LocalDate ledgerDate, Long ledgerAmount, String ledgerDescription, String ownerNickname) {
        super(ledgerId, ledgerCode, ledgerDate, ledgerAmount, ledgerDescription);
        this.ownerNickname = ownerNickname;
    }
}
