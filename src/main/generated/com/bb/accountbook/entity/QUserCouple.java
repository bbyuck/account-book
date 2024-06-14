package com.bb.accountbook.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserCouple is a Querydsl query type for UserCouple
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserCouple extends EntityPathBase<UserCouple> {

    private static final long serialVersionUID = 2097332925L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserCouple userCouple = new QUserCouple("userCouple");

    public final QCouple couple;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final EnumPath<com.bb.accountbook.common.model.status.UserCoupleStatus> status = createEnum("status", com.bb.accountbook.common.model.status.UserCoupleStatus.class);

    public final QUser user;

    public QUserCouple(String variable) {
        this(UserCouple.class, forVariable(variable), INITS);
    }

    public QUserCouple(Path<? extends UserCouple> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserCouple(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserCouple(PathMetadata metadata, PathInits inits) {
        this(UserCouple.class, metadata, inits);
    }

    public QUserCouple(Class<? extends UserCouple> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.couple = inits.isInitialized("couple") ? new QCouple(forProperty("couple")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

