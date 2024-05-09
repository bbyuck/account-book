package com.bb.accountbook.common.model.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonCode {
    /**
     * SYSTEM Error
     */
    ERR_SYS_000("시스템 오류입니다. 관리자에게 문의해주세요."),
    ERR_SYS_001("API를 찾을 수 없습니다."),

    /**
     * USER Error
     */
    ERR_USER_001("이미 가입된 메일입니다.");

    private final String value;
}
