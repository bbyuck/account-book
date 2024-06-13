package com.bb.accountbook.common.model.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.bb.accountbook.common.constant.AdditionalHttpStatus.UNPROCESSABLE_ENTITY;
import static jakarta.servlet.http.HttpServletResponse.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {


    /**
     * SYSTEM Error
     */
    ERR_SYS_000("시스템 오류입니다. 관리자에게 문의해주세요.", SC_INTERNAL_SERVER_ERROR),
    ERR_SYS_001("API를 찾을 수 없습니다.", SC_NOT_FOUND),
    ERR_SYS_002("정적 자원을 찾을 수 없습니다.", SC_NOT_FOUND),
    ERR_SYS_003("파라미터의 입력이 올바르지 않습니다.", SC_BAD_REQUEST),


    /**
     * USER Error
     */
    ERR_USR_000("유저를 찾을 수 없습니다.", SC_NOT_FOUND),
    ERR_USR_001("이미 가입된 메일입니다.", SC_CONFLICT),
    ERR_USR_002("계정이 활성화되지 않았습니다.", SC_CONFLICT),
    /**
     * Couple Error
     */
    ERR_CPL_000("선택한 커플에 연결 된 유저수가 정확하지 않습니다.", UNPROCESSABLE_ENTITY),
    ERR_CPL_001("커플 정보를 찾을 수 없습니다.", SC_NOT_FOUND),
    ERR_CPL_002("이미 커플로 연결되어 있습니다.", SC_CONFLICT),
    ERR_CPL_003("커플 매핑 정보를 찾을 수 없습니다.", SC_NOT_FOUND),
    ERR_CPL_004("커플 상태가 활성 상태가 아닙니다.", SC_CONFLICT),

    /**
     * Ledger Error
     */
    ERR_LED_000("가계부 상세 항목을 찾을 수 없습니다.", SC_NOT_FOUND),
    ERR_LED_001("가계부 금액은 0원 이상이어야 합니다.", UNPROCESSABLE_ENTITY),

    /**
     * Authentication, Authorization
     */
    ERR_AUTH_000("Security Context에 인증 정보가 없습니다.", SC_UNAUTHORIZED),
    ERR_AUTH_001("잘못된 ID/PW 입니다.", SC_UNAUTHORIZED),
    ERR_AUTH_002("권한이 없습니다.", SC_FORBIDDEN),
    ERR_AUTH_003("인증에 실패했습니다.", SC_UNAUTHORIZED),
    ERR_AUTH_004("잘못된 JWT 서명입니다.", SC_UNAUTHORIZED),
    ERR_AUTH_005("만료된 JWT 토큰입니다.", SC_UNAUTHORIZED),
    ERR_AUTH_006("지원되지 않는 JWT 토큰입니다.", SC_UNAUTHORIZED),
    ERR_AUTH_007("잘못된 JWT 토큰 입력입니다.", SC_UNAUTHORIZED),

    /**
     * Customization
     */
    ERR_CUS_000("커스텀 정보를 찾을 수 없습니다.", SC_NOT_FOUND),

    ;

    private final String value;
    private final int httpStatus;

}
