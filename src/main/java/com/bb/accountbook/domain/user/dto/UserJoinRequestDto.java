package com.bb.accountbook.domain.user.dto;

import jakarta.annotation.Nonnull;
import lombok.Data;

@Data
public class UserJoinRequestDto {

    @Nonnull
    private String email;

    @Nonnull
    private String password;
}
