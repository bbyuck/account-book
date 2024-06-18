package com.bb.accountbook.domain.user.dto;

import com.bb.accountbook.common.constant.ValidationMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank(message = ValidationMessage.ERR_VALID_001)    // 이메일을 입력해주세요.
    @Email(message = ValidationMessage.ERR_VALID_000)       // 잘못된 메일 입력입니다.
    private String email;

    @NotBlank(message = ValidationMessage.ERR_VALID_002)    // 패스워드를 입력해주세요.
    private String password;

    private boolean autoLogin = false;
}
