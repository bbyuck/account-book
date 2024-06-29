package com.bb.accountbook.domain.icon.dto;

import com.bb.accountbook.entity.Icon;
import lombok.Data;

@Data
public class IconDto {
    private Long iconId;
    private String iconName;

    public IconDto(Icon icon) {
        this.iconId = icon.getId();
        this.iconName = icon.getName();
    }
}
