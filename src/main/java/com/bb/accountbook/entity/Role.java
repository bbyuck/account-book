package com.bb.accountbook.entity;

import com.bb.accountbook.common.model.codes.RoleCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "tb_role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "tb_role_id_generator",
        sequenceName = "seq_role",
        initialValue = 1, allocationSize = 50)
public class Role extends BaseEntity {

    @Id @GeneratedValue(generator = "seq_role")
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_code")
    @Enumerated(EnumType.STRING)
    private RoleCode code;

    @OneToMany(mappedBy = "role")
    private List<RoleMenu> roleMenus = new ArrayList<>();

    public Role(RoleCode code) {
        this.code = code;
    }
}
