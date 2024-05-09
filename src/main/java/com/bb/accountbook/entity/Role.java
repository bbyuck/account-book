package com.bb.accountbook.entity;

import com.bb.accountbook.common.model.codes.RoleCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

import static com.bb.accountbook.common.model.codes.RoleCode.*;

@Entity
@Getter
@Table(name = "tb_role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleCode name;

    public Role(RoleCode roleCode) {
        this.name = roleCode;
    }
}
