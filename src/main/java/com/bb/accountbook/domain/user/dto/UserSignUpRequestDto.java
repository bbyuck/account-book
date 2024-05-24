package com.bb.accountbook.domain.user.dto;

import com.bb.accountbook.common.constant.ValidationMessage;
import com.bb.accountbook.common.model.codes.GenderCode;
import com.bb.accountbook.common.validation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserSignUpRequestDto {

    @Email(message = ValidationMessage.INVALID_EMAIL)
    private String email;

    @Password
    private String password;

    @NotNull
    private GenderCode gender;
}
