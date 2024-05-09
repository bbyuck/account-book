package com.bb.accountbook.common.model.codes;

import lombok.Getter;

import java.util.List;

public enum RoleCode {
    /** 기본 유저 */
    BASIC,
    /** 관리자 */
    ADMIN;

    public final static List<RoleCode> DEFAULT = List.of(BASIC);
}
