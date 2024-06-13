package com.bb.accountbook.common.validation.constraints;

import com.bb.accountbook.common.validation.YearMonthValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = YearMonthValidator.class)
public @interface YearMonth {

    String message() default "잘못된 연-월 입력입니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
