package com.bb.accountbook.domain.ledger.dto;

import com.bb.accountbook.common.model.codes.LedgerCode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LedgerUpdateRequestDto {
    @NotBlank
    private LocalDate ledgerDate;

    @NotBlank
    private LedgerCode ledgerCode;

    @NotBlank
    private Long ledgerAmount = 0L;

    private String ledgerDescription;
}
