package com.bb.accountbook.domain.icon.dto;

import lombok.Data;

import java.util.List;

@Data
public class MultiIconInsertRequestDto {
    private List<IconInsertRequestDto> list;
}
