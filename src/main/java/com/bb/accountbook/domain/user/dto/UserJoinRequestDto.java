package com.bb.accountbook.domain.user.dto;

import com.bb.accountbook.common.constant.ValidationMessage;
import com.bb.accountbook.common.model.codes.GenderCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserJoinRequestDto {

    @Email(message = ValidationMessage.INVALID_EMAIL)
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private GenderCode gender;
}
