package com.bb.accountbook.domain.icon.dto;

import lombok.Data;

@Data
public class IconInsertResponseDto {
    private boolean success;
    private int count;

    public IconInsertResponseDto(int count) {
        this.success = true;
        this.count = count;
    }
}
