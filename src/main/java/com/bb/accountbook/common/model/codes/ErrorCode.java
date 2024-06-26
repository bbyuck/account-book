package com.bb.accountbook.common.model.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

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
    ERR_USR_003("잘못된 비밀번호 입력입니다.", SC_BAD_REQUEST),
    ERR_USR_004("동일한 비밀번호로 변경할 수 없습니다.", SC_BAD_REQUEST),
    /**
     * Couple Error
     */
    ERR_CPL_000("상대를 찾을 수 없습니다.", SC_BAD_REQUEST),
    ERR_CPL_001("커플 정보를 찾을 수 없습니다.", SC_NOT_FOUND),
    ERR_CPL_002("이미 커플로 등록이 되어있습니다.", SC_CONFLICT),
    ERR_CPL_003("커플 매핑 정보를 찾을 수 없습니다.", SC_NOT_FOUND),
    ERR_CPL_004("커플 상태가 활성 상태가 아닙니다.", SC_CONFLICT),
    ERR_CPL_005("상대가 이미 커플로 등록되어 있습니다.", SC_BAD_REQUEST),
    ERR_CPL_006("커플 정보가 정확하지 않습니다.", SC_INTERNAL_SERVER_ERROR),
    ERR_CPL_007("받은 요청을 찾을 수 없습니다.", SC_NOT_FOUND),

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
    ERR_AUTH_002("접근 권한이 없습니다.", SC_FORBIDDEN),
    ERR_AUTH_003("인증에 실패했습니다.", SC_UNAUTHORIZED),
    ERR_AUTH_004("잘못된 JWT 서명입니다.", SC_UNAUTHORIZED),
    ERR_AUTH_005("만료된 JWT 토큰입니다.", SC_UNAUTHORIZED),
    ERR_AUTH_006("지원되지 않는 JWT 토큰입니다.", SC_UNAUTHORIZED),
    ERR_AUTH_007("잘못된 JWT 토큰 입력입니다.", SC_UNAUTHORIZED),

    /**
     * Customization
     */
    ERR_CUS_000("커스텀 정보를 찾을 수 없습니다.", SC_NOT_FOUND),

    /**
     * Validation
     */
    ERR_VALID_000("잘못된 메일 입력입니다.", SC_BAD_REQUEST),
    ERR_VALID_001("이메일을 입력해주세요.", SC_BAD_REQUEST),
    ERR_VALID_002("패스워드를 입력해주세요.", SC_BAD_REQUEST),
    ERR_VALID_003("잘못된 패스워드 입력입니다.\\n 영문 / 숫자 / 특수문자를 각각 1자 이상 포함하여 8~16자로 입력해주세요.", SC_BAD_REQUEST),
    ERR_VALID_004("패스워드가 다릅니다.", SC_BAD_REQUEST),
    ERR_VALID_005("패스워드 확인을 입력해주세요.", SC_BAD_REQUEST),
    ERR_VALID_006("새 패스워드를 입력해주세요.", SC_BAD_REQUEST),

    /**
     * Mail (AWS SES)
     */
    ERR_MAIL_000("메일 발송에 실패했습니다.", SC_INTERNAL_SERVER_ERROR),
    ERR_MAIL_001("메일 Form을 읽어오지 못했습니다.", SC_INTERNAL_SERVER_ERROR),
    ERR_MAIL_002("메일을 찾지 못했습니다.", SC_NOT_FOUND),
    ERR_MAIL_003("유효하지 않은 메일입니다.", SC_BAD_REQUEST),
    ERR_MAIL_004("메일의 유효 시간이 지났습니다.", SC_BAD_REQUEST),

    ;


    private final String value;
    private final int httpStatus;

    public static class Validation {
        public static final Map<String, ErrorCode> map = Map.of(
                ERR_VALID_000.name(), ERR_VALID_000,
                ERR_VALID_001.name(), ERR_VALID_001,
                ERR_VALID_002.name(), ERR_VALID_002,
                ERR_VALID_003.name(), ERR_VALID_003,
                ERR_VALID_004.name(), ERR_VALID_004,
                ERR_VALID_005.name(), ERR_VALID_005,
                ERR_VALID_006.name(), ERR_VALID_006
        );
    }
}
