package com.bb.accountbook.domain.custom.dto;

import com.bb.accountbook.common.model.codes.CustomCode;
import lombok.Data;

@Data
public class CustomCreateRequestDto {
    private CustomCode customCode;
    private String customValue;
}
