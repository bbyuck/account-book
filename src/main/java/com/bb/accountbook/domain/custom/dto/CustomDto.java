package com.bb.accountbook.domain.custom.dto;

import com.bb.accountbook.common.model.codes.CustomCode;
import lombok.Data;

@Data
public class CustomDto {
    private Long customId;
    private CustomCode customCode;
    private String customValue;

    public CustomDto(Long customId, CustomCode customCode, String customValue) {
        this.customId = customId;
        this.customCode = customCode;
        this.customValue = customValue;
    }
}
