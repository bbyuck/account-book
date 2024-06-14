package com.bb.accountbook.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustom is a Querydsl query type for Custom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustom extends EntityPathBase<Custom> {

    private static final long serialVersionUID = 1932155843L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustom custom = new QCustom("custom");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final EnumPath<com.bb.accountbook.common.model.codes.CustomCode> code = createEnum("code", com.bb.accountbook.common.model.codes.CustomCode.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final QUser user;

    public final StringPath value = createString("value");

    public QCustom(String variable) {
        this(Custom.class, forVariable(variable), INITS);
    }

    public QCustom(Path<? extends Custom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustom(PathMetadata metadata, PathInits inits) {
        this(Custom.class, metadata, inits);
    }

    public QCustom(Class<? extends Custom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

