package com.bb.accountbook.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoleMenu is a Querydsl query type for RoleMenu
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoleMenu extends EntityPathBase<RoleMenu> {

    private static final long serialVersionUID = 502915783L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoleMenu roleMenu = new QRoleMenu("roleMenu");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMenu menu;

    public final QRole role;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QRoleMenu(String variable) {
        this(RoleMenu.class, forVariable(variable), INITS);
    }

    public QRoleMenu(Path<? extends RoleMenu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoleMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoleMenu(PathMetadata metadata, PathInits inits) {
        this(RoleMenu.class, metadata, inits);
    }

    public QRoleMenu(Class<? extends RoleMenu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.menu = inits.isInitialized("menu") ? new QMenu(forProperty("menu"), inits.get("menu")) : null;
        this.role = inits.isInitialized("role") ? new QRole(forProperty("role")) : null;
    }

}

