package com.bb.accountbook.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCause is a Querydsl query type for Cause
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCause extends EntityPathBase<Cause> {

    private static final long serialVersionUID = 1862848983L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCause cause = new QCause("cause");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.bb.accountbook.common.model.codes.LedgerCode> ledgerCode = createEnum("ledgerCode", com.bb.accountbook.common.model.codes.LedgerCode.class);

    public final StringPath name = createString("name");

    public final QUser owner;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QCause(String variable) {
        this(Cause.class, forVariable(variable), INITS);
    }

    public QCause(Path<? extends Cause> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCause(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCause(PathMetadata metadata, PathInits inits) {
        this(Cause.class, metadata, inits);
    }

    public QCause(Class<? extends Cause> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.owner = inits.isInitialized("owner") ? new QUser(forProperty("owner"), inits.get("owner")) : null;
    }

}

