package com.bb.accountbook.domain.user.dto;

import com.bb.accountbook.common.model.codes.GenderCode;
import com.bb.accountbook.common.validation.constraints.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserSignUpRequestDto {

    @Email(message = "ERR_VALID_000")
    private String email;

    @Password(message = "ERR_VALID_003")
    private String password;

    @NotNull
    private GenderCode gender;
}
