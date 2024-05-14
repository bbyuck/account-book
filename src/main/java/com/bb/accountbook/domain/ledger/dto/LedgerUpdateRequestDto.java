package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LedgerUpdateRequestDto {
    @Nonnull
    private LocalDate ledgerDate;

    @Nonnull
    private LedgerCode ledgerCode;

    @Nonnull
    private Long ledgerAmount = 0L;


    private String ledgerDescription;
}
