package com.bb.accountbook.common.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordValidatorTest {
    PasswordValidator passwordValidator = new PasswordValidator();

    @Test
    @DisplayName("특수문자 없음")
    void no_special() {
        // given
        String password = "abcd1234";

        // when
        boolean valid = passwordValidator.isValid(password, null);

        // then
        assertThat(valid).isFalse();
    }

    @Test
    @DisplayName("길이가 최소 글자수 이하")
    void under_minimum_length() {
        // given
        String password = "abcd12*";

        // when
        boolean valid = passwordValidator.isValid(password, null);

        // then
        assertThat(valid).isFalse();
    }

    @Test
    @DisplayName("허용되지 않는 문자 포함")
    void not_permitted_character() {
        // given

        // then
        assertThat(passwordValidator.isValid(" abcd1234**", null)).isFalse(); // 공백문자
        assertThat(passwordValidator.isValid("한글포함1234**", null)).isFalse(); //한글포함
    }

    @Test
    @DisplayName("대/소문자, 숫자, 특수문자 를 모두 포함하지 않은 경우")
    void not_satisfied_with() {
        assertThat(passwordValidator.isValid("abcd1234", null)).isFalse();  // 특수문자 없음
        assertThat(passwordValidator.isValid("12345678**", null)).isFalse(); // 대/소문자 없음
        assertThat(passwordValidator.isValid("abcdefgh**", null)).isFalse(); // 숫자 없음
    }

    @Test
    @DisplayName("정상")
    void ok() {
        assertThat(passwordValidator.isValid("Abcd12**", null)).isTrue();
    }

}