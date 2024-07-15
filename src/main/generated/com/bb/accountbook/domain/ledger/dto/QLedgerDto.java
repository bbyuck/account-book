package com.bb.accountbook.domain.ledger.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.bb.accountbook.domain.ledger.dto.QLedgerDto is a Querydsl Projection type for LedgerDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QLedgerDto extends ConstructorExpression<LedgerDto> {

    private static final long serialVersionUID = 168485107L;

    public QLedgerDto(com.querydsl.core.types.Expression<Long> ledgerId, com.querydsl.core.types.Expression<com.bb.accountbook.common.model.codes.LedgerCode> ledgerCode, com.querydsl.core.types.Expression<Long> amount, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<String> color, com.querydsl.core.types.Expression<? extends LedgerCategoryDto> category, com.querydsl.core.types.Expression<java.time.LocalDate> date) {
        super(LedgerDto.class, new Class<?>[]{long.class, com.bb.accountbook.common.model.codes.LedgerCode.class, long.class, String.class, String.class, LedgerCategoryDto.class, java.time.LocalDate.class}, ledgerId, ledgerCode, amount, description, color, category, date);
    }

}

