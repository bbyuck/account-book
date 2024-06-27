package com.bb.accountbook.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLedger is a Querydsl query type for Ledger
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLedger extends EntityPathBase<Ledger> {

    private static final long serialVersionUID = -2120385093L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLedger ledger = new QLedger("ledger");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final EnumPath<com.bb.accountbook.common.model.codes.LedgerCode> code = createEnum("code", com.bb.accountbook.common.model.codes.LedgerCode.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLedgerCategory ledgerCategory;

    public final QUser owner;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QLedger(String variable) {
        this(Ledger.class, forVariable(variable), INITS);
    }

    public QLedger(Path<? extends Ledger> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLedger(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLedger(PathMetadata metadata, PathInits inits) {
        this(Ledger.class, metadata, inits);
    }

    public QLedger(Class<? extends Ledger> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ledgerCategory = inits.isInitialized("ledgerCategory") ? new QLedgerCategory(forProperty("ledgerCategory"), inits.get("ledgerCategory")) : null;
        this.owner = inits.isInitialized("owner") ? new QUser(forProperty("owner"), inits.get("owner")) : null;
    }

}

