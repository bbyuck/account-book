package com.bb.accountbook.common.validation.constraints;

import com.bb.accountbook.common.validation.PasswordConfirmValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordConfirmValidator.class)
public @interface PasswordConfirm {

    String message() default "패스워드가 다릅니다.";

    Class[] groups() default {};

    Class[] payload() default {};
    String passwordFieldName();
    String passwordConfirmFieldName();
}
