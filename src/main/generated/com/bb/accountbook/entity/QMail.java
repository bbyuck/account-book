package com.bb.accountbook.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMail is a Querydsl query type for Mail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMail extends EntityPathBase<Mail> {

    private static final long serialVersionUID = -1463631223L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMail mail = new QMail("mail");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final EnumPath<com.bb.accountbook.common.model.codes.MailCode> code = createEnum("code", com.bb.accountbook.common.model.codes.MailCode.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser receiver;

    public final DateTimePath<java.time.LocalDateTime> sentDateTime = createDateTime("sentDateTime", java.time.LocalDateTime.class);

    public final EnumPath<com.bb.accountbook.common.model.status.MailStatus> status = createEnum("status", com.bb.accountbook.common.model.status.MailStatus.class);

    public final NumberPath<Integer> ttl = createNumber("ttl", Integer.class);

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QMail(String variable) {
        this(Mail.class, forVariable(variable), INITS);
    }

    public QMail(Path<? extends Mail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMail(PathMetadata metadata, PathInits inits) {
        this(Mail.class, metadata, inits);
    }

    public QMail(Class<? extends Mail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiver = inits.isInitialized("receiver") ? new QUser(forProperty("receiver"), inits.get("receiver")) : null;
    }

}

