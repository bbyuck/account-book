package com.bb.accountbook.common.validation.presentation;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.validation.UserValidation;
import com.bb.accountbook.common.validation.presentation.constraints.PasswordConfirm;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

import static com.bb.accountbook.common.model.codes.ErrorCode.ERR_SYS_000;


@Slf4j
public class PasswordConfirmValidator implements ConstraintValidator<PasswordConfirm, Object> {

    private String passwordFieldName;
    private String passwordConfirmFieldName;

    private String getFieldValue(Object object, String fieldName) {
        Class<?> clazz = object.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (String) field.get(object);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            log.debug(e.getMessage(), e);
            throw new GlobalException(ERR_SYS_000);
        }
    }

    @Override
    public void initialize(PasswordConfirm constraintAnnotation) {
        this.passwordFieldName = constraintAnnotation.passwordFieldName();
        this.passwordConfirmFieldName = constraintAnnotation.passwordConfirmFieldName();

        if (StringUtils.isBlank(this.passwordFieldName) || StringUtils.isBlank(this.passwordConfirmFieldName)) {
            log.error("password, passwordConfirm 필드명을 입력하지 않았습니다.");
            throw new GlobalException(ERR_SYS_000);
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return UserValidation.passwordConfirmValidation(getFieldValue(value, passwordFieldName), getFieldValue(value, passwordConfirmFieldName));
    }
}
