package com.bb.accountbook.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLedgerCause is a Querydsl query type for LedgerCause
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLedgerCause extends EntityPathBase<LedgerCause> {

    private static final long serialVersionUID = -1992236178L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLedgerCause ledgerCause = new QLedgerCause("ledgerCause");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCause cause;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLedger ledger;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QLedgerCause(String variable) {
        this(LedgerCause.class, forVariable(variable), INITS);
    }

    public QLedgerCause(Path<? extends LedgerCause> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLedgerCause(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLedgerCause(PathMetadata metadata, PathInits inits) {
        this(LedgerCause.class, metadata, inits);
    }

    public QLedgerCause(Class<? extends LedgerCause> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cause = inits.isInitialized("cause") ? new QCause(forProperty("cause"), inits.get("cause")) : null;
        this.ledger = inits.isInitialized("ledger") ? new QLedger(forProperty("ledger"), inits.get("ledger")) : null;
    }

}

