package com.bb.accountbook.common.validation;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserValidation {

    public static boolean passwordValidation(String value) {
        final String UPPER_CASE = "^(?=.*[A-Z]).*$";
        final String LOWER_CASE = "^(?=.*[a-z]).*$";
        final String DIGIT = "^(?=.*\\d).+$";
        final String SPECIAL_CHARACTER = "^(?=.*[!@#$%^&*()-+=]).+$";

        final String PERMITTED_CHARACTER = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\|,.<>/?~`]+$";

        final int MINIMUM_LENGTH = 8;

        final int MAXIMUM_LENGTH = 16;

        int cnt = 0;

        if (value.length() < MINIMUM_LENGTH || value.length() > MAXIMUM_LENGTH) {
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

    public static boolean passwordConfirmValidation(String value1, String value2) {
        if (value1 == null || value2 == null) {
            log.debug("패스워드 혹은 패스워드 확인 입력으로 null이 입력되었습니다.");
            throw new GlobalException(ErrorCode.ERR_VALID_003);
        }

        return value1.equals(value2);
    }
}
