package com.bb.accountbook.common.model.codes;


import java.util.List;

public enum RoleCode {
    /** 기본 유저 */
    ROLE_USER,
    /** 관리자 */
    ROLE_ADMIN;

    public final static List<RoleCode> DEFAULT = List.of(ROLE_USER);
}
