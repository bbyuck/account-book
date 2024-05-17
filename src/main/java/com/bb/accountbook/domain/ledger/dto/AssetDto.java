package com.bb.accountbook.domain.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssetDto {

    private Long amount;

    public void add(Long amount) {
        this.amount += amount;
    }
}
