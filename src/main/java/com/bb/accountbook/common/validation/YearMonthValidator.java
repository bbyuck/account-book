package com.bb.accountbook.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class YearMonthValidator implements ConstraintValidator<YearMonth, String> {

    private final String targetRegex = "^(?:19|20)\\d\\d(0[1-9]|1[0-2])$"; // yyyyMM

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(targetRegex);
    }
}
