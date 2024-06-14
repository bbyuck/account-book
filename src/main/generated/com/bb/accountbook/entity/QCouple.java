package com.bb.accountbook.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCouple is a Querydsl query type for Couple
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCouple extends EntityPathBase<Couple> {

    private static final long serialVersionUID = 1926670354L;

    public static final QCouple couple = new QCouple("couple");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final EnumPath<com.bb.accountbook.common.model.status.CoupleStatus> status = createEnum("status", com.bb.accountbook.common.model.status.CoupleStatus.class);

    public final ListPath<UserCouple, QUserCouple> userCouples = this.<UserCouple, QUserCouple>createList("userCouples", UserCouple.class, QUserCouple.class, PathInits.DIRECT2);

    public QCouple(String variable) {
        super(Couple.class, forVariable(variable));
    }

    public QCouple(Path<? extends Couple> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCouple(PathMetadata metadata) {
        super(Couple.class, metadata);
    }

}

