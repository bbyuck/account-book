package com.bb.accountbook.domain.ledger.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.bb.accountbook.domain.ledger.dto.QLedgerCategoryDto is a Querydsl Projection type for LedgerCategoryDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QLedgerCategoryDto extends ConstructorExpression<LedgerCategoryDto> {

    private static final long serialVersionUID = 897535765L;

    public QLedgerCategoryDto(com.querydsl.core.types.Expression<Long> ledgerCategoryId, com.querydsl.core.types.Expression<String> ledgerCategoryName, com.querydsl.core.types.Expression<Long> iconId, com.querydsl.core.types.Expression<String> iconName, com.querydsl.core.types.Expression<com.bb.accountbook.common.model.codes.LedgerCode> ledgerCode) {
        super(LedgerCategoryDto.class, new Class<?>[]{long.class, String.class, long.class, String.class, com.bb.accountbook.common.model.codes.LedgerCode.class}, ledgerCategoryId, ledgerCategoryName, iconId, iconName, ledgerCode);
    }

}

