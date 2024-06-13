package com.bb.accountbook.domain.user.dto;

import com.bb.accountbook.common.validation.constraints.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserSignUpRequestDto {

    @Email(message = "ERR_VALID_000")
    @NotBlank(message = "ERR_VALID_001")
    private String email;

    @Password(message = "ERR_VALID_003")
    @NotBlank(message = "ERR_VALID_002")
    private String password;

    @Password(message = "ERR_VALID_004")
    @NotBlank(message = "ERR_VALID_005")
    private String confirmPassword;

}
