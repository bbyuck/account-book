package com.bb.accountbook.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tb_icon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "tb_icon_id_generator",
        sequenceName = "seq_icon",
        initialValue = 1, allocationSize = 50)
public class Icon extends BaseEntity {

    @Id @GeneratedValue(generator = "seq_icon")
    @Column(name = "icon_id")
    private Long id;

    @Column(name = "icon_name")
    private String name;

    public void change(String name) {
        this.name = name;
    }

    public Icon(String name) {
        this.name = name;
    }

}
