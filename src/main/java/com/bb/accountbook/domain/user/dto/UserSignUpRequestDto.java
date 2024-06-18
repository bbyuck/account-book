package com.bb.accountbook.domain.user.dto;

import com.bb.accountbook.common.constant.ValidationMessage;
import com.bb.accountbook.common.validation.presentation.constraints.Password;
import com.bb.accountbook.common.validation.presentation.constraints.PasswordConfirm;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@PasswordConfirm(passwordFieldName = "password"
        , passwordConfirmFieldName = "passwordConfirm"
        , message = ValidationMessage.ERR_VALID_004)    // 패스워드가 다릅니다.
public class UserSignUpRequestDto {

    @NotBlank(message = ValidationMessage.ERR_VALID_001)    // 이메일을 입력해주세요.
    @Email(message = ValidationMessage.ERR_VALID_000)       // 잘못된 메일 입력입니다.
    private String email;

    @NotBlank(message = ValidationMessage.ERR_VALID_002)    // 패스워드를 입력해주세요.
    @Password(message = ValidationMessage.ERR_VALID_003)    // 잘못된 패스워드 양식입니다.
    private String password;

    @NotBlank(message = ValidationMessage.ERR_VALID_005)    // 패스워드 확인을 입력해주세요.
    private String passwordConfirm;

}
