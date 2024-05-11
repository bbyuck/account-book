package com.bb.accountbook.common.model.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static com.bb.accountbook.common.model.codes.MemberCode.*;


@Getter
@RequiredArgsConstructor
public enum GroupCode {
    MARRIED_COUPLE("부부", Set.of(HUSBAND, WIFE)),;

    private final String value;
    private final Set<MemberCode> memberCodes;
}
