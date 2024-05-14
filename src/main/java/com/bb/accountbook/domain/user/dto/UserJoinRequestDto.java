package com.bb.accountbook.domain.user.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserJoinRequestDto {

    @Email(message = "잘못된 메일 형식입니다.")
    private String email;

    @Nonnull
    private String password;
}
