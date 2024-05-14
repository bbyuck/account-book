package com.bb.accountbook.entity;

import com.bb.accountbook.common.model.codes.GroupCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tb_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {

    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    @Column(name = "group_name")
    private String name;

    @Column(name = "group_code", length = 20, nullable = false)
    private GroupCode groupCode;

    public Group(String name, GroupCode code) {
        this.name = name;
        this.groupCode = code;
    }

}
