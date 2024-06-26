package com.bb.accountbook.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminLoginResponseDto {
    private TokenDto token;
    private String email;
}
