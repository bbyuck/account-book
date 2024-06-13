package com.bb.accountbook.domain.user.dto;

import com.bb.accountbook.common.constant.ValidationMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @Email(message = ValidationMessage.INVALID_EMAIL)
    @NotBlank(message = ValidationMessage.BLANK_EMAIL)
    private String email;

    @NotBlank(message = ValidationMessage.BLANK_PASSWORD)
    private String password;

    private boolean autoLogin = false;
}
