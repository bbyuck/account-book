package com.bb.accountbook.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVerifyRequestDto {

    @NotBlank
    private String target;
}
