package com.bb.accountbook.common.validation;

import com.bb.accountbook.common.validation.constraints.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    private final String UPPER_CASE = "^(?=.*[A-Z]).*$";
    private final String LOWER_CASE = "^(?=.*[z-z]).*$";
    private final String DIGIT = "^(?=.*\\d).+$";
    private final String SPECIAL_CHARACTER = "^(?=.*[!@#$%^&*()-+=]).+$";

    private final String PERMITTED_CHARACTER = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\|,.<>/?~`]+$";

    private final int MINIMUM_LENGTH = 8;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        int cnt = 0;

        if (value.length() < MINIMUM_LENGTH) {
            return false;
        }

        if (!value.matches(PERMITTED_CHARACTER)) {
            return false;
        }

        if (value.matches(UPPER_CASE) || value.matches(LOWER_CASE)) {
            cnt++;
        }
        if (value.matches(DIGIT)) {
            cnt++;
        }
        if (value.matches(SPECIAL_CHARACTER)) {
            cnt++;
        }

        return cnt == 3;
    }
}
