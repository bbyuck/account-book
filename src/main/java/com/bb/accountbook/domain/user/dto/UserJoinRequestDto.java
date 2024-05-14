package com.bb.accountbook.domain.user.dto;

import com.bb.accountbook.common.model.codes.GenderCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserJoinRequestDto {

    @Email(message = "잘못된 메일 형식입니다.")
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private GenderCode gender;
}
