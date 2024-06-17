package com.bb.accountbook.common.model.codes;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    SUC_USR_000("가입이 완료되었습니다.", SC_OK),
    SUC_USR_001("본인 인증 메일이 발송되었습니다.\n메일을 확인하여 본인 인증을 진행해주세요.\n본인 인증을 완료해야 가입이 완료됩니다.", SC_OK),
    SUC_USR_002("로그아웃 되었습니다.", SC_OK),
    SUC_USR_003("비밀번호가 변경되었습니다.\n다시 로그인해주세요.", SC_OK),
    USC_USR_004("본인 인증이 완료되었습니다.", SC_OK),

    ;

    private final String value;
    private final int httpStatus;
}
