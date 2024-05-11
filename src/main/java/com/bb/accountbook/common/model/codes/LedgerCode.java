package com.bb.accountbook.common.model.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LedgerCode {
    /* INCOME : 소득 */
    I("소득"),
    /* EXPENDITURE : 지출 */
    E("지출"),
    /* SAVING : 저축 */
    S("저축");

    private final String value;
}
