package com.bb.accountbook.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLedgerCategory is a Querydsl query type for LedgerCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLedgerCategory extends EntityPathBase<LedgerCategory> {

    private static final long serialVersionUID = 1403696089L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLedgerCategory ledgerCategory = new QLedgerCategory("ledgerCategory");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final QIcon icon;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.bb.accountbook.common.model.codes.LedgerCode> ledgerCode = createEnum("ledgerCode", com.bb.accountbook.common.model.codes.LedgerCode.class);

    public final StringPath name = createString("name");

    public final QUser owner;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QLedgerCategory(String variable) {
        this(LedgerCategory.class, forVariable(variable), INITS);
    }

    public QLedgerCategory(Path<? extends LedgerCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLedgerCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLedgerCategory(PathMetadata metadata, PathInits inits) {
        this(LedgerCategory.class, metadata, inits);
    }

    public QLedgerCategory(Class<? extends LedgerCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.icon = inits.isInitialized("icon") ? new QIcon(forProperty("icon")) : null;
        this.owner = inits.isInitialized("owner") ? new QUser(forProperty("owner"), inits.get("owner")) : null;
    }

}

