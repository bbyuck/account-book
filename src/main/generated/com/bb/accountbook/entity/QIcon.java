package com.bb.accountbook.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIcon is a Querydsl query type for Icon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIcon extends EntityPathBase<Icon> {

    private static final long serialVersionUID = -1463748277L;

    public static final QIcon icon = new QIcon("icon");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath srcPath = createString("srcPath");

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QIcon(String variable) {
        super(Icon.class, forVariable(variable));
    }

    public QIcon(Path<? extends Icon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIcon(PathMetadata metadata) {
        super(Icon.class, metadata);
    }

}

