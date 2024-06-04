package com.bb.accountbook.domain.user.dto;

import com.bb.accountbook.common.constant.ValidationMessage;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginDto {

    @Email(message = ValidationMessage.INVALID_EMAIL)
    private String email;
    private String password;
    private boolean autoLogin = false;
}
