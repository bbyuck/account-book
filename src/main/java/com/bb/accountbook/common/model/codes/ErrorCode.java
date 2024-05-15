package com.bb.accountbook.common.model.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /**
     * SYSTEM Error
     */
    ERR_SYS_000("시스템 오류입니다. 관리자에게 문의해주세요."),
    ERR_SYS_001("API를 찾을 수 없습니다."),

    /**
     * USER Error
     */
    ERR_USR_000("유저를 찾을 수 없습니다."),
    ERR_USR_001("이미 가입된 메일입니다."),
    /**
     * Couple Error
     */
    ERR_CPL_000("선택한 커플 Entity에 연결 된 유저수가 정확하지 않습니다."),
    ERR_CPL_001("커플 Entity를 찾을 수 없습니다."),
    ERR_CPL_002("이미 커플로 연결되어 있습니다."),
    ERR_CPL_003("커플 매핑 정보를 찾을 수 없습니다."),

    /**
     * Ledger Error
     */
    ERR_LED_000("가계부 상세 항목을 찾을 수 없습니다."),
    ;

    private final String value;
}
