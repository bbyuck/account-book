package com.bb.accountbook.common.validation.presentation.constraints;

import com.bb.accountbook.common.validation.presentation.PasswordValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

    String message() default "잘못된 패스워드 양식입니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
