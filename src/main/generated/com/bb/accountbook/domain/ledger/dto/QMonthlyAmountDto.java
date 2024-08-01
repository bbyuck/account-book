package com.bb.accountbook.domain.ledger.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.bb.accountbook.domain.ledger.dto.QMonthlyAmountDto is a Querydsl Projection type for MonthlyAmountDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMonthlyAmountDto extends ConstructorExpression<MonthlyAmountDto> {

    private static final long serialVersionUID = -42780419L;

    public QMonthlyAmountDto(com.querydsl.core.types.Expression<Integer> year, com.querydsl.core.types.Expression<Integer> month, com.querydsl.core.types.Expression<Long> amount, com.querydsl.core.types.Expression<com.bb.accountbook.common.model.codes.LedgerCode> ledgerCode) {
        super(MonthlyAmountDto.class, new Class<?>[]{int.class, int.class, long.class, com.bb.accountbook.common.model.codes.LedgerCode.class}, year, month, amount, ledgerCode);
    }

}

