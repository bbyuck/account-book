package com.bb.accountbook.domain.icon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class IconFindResponseDto {
    private List<IconDto> icons;
}
