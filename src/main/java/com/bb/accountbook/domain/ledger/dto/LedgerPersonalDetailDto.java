package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class LedgerPersonalDetailDto extends LedgerDetailDto {
    public LedgerPersonalDetailDto(Long ledgerId, LedgerCode ledgerCode, LocalDate ledgerDate, Long ledgerAmount, String ledgerDescription) {
        super(ledgerId, ledgerCode, ledgerDate, ledgerAmount, ledgerDescription);
    }
}
