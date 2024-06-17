package com.bb.accountbook.common.validation.presentation;

import com.bb.accountbook.common.validation.UserValidation;
import com.bb.accountbook.common.validation.presentation.constraints.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return UserValidation.passwordValidation(value);
    }
}
