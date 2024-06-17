package com.bb.accountbook.domain.user.dto;

import com.bb.accountbook.common.validation.presentation.constraints.Password;
import com.bb.accountbook.common.validation.presentation.constraints.PasswordConfirm;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@PasswordConfirm(passwordFieldName = "password"
        , passwordConfirmFieldName = "passwordConfirm"
        , message = "ERR_VALID_004")    // 패스워드가 다릅니다.
public class UserPasswordChangeRequestDto {

    @NotBlank(message = "ERR_VALID_002")    // 패스워드를 입력해주세요.
    @Password(message = "ERR_VALID_003")    // 잘못된 패스워드 양식입니다.
    private String password;

    @NotBlank(message = "ERR_VALID_005")    // 패스워드 확인을 입력해주세요.
    private String passwordConfirm;
}
