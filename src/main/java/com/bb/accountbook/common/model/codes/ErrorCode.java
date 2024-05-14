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
     * Group Error
     */
    ERR_GRP_000("선택한 그룹에 존재하지 않는 멤버 코드입니다."),
    ERR_GRP_001("그룹을 찾을 수 없습니다."),
    ERR_GRP_002("이미 가입된 그룹입니다."),

    /**
     * Ledger Error
     */
    ERR_LED_000("가계부 상세 항목을 찾을 수 없습니다."),
    ;

    private final String value;
}
