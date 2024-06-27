package com.bb.accountbook.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tb_menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "tb_menu_id_generator",
        sequenceName = "seq_menu",
        initialValue = 1, allocationSize = 50)
public class Menu extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    @Column(name = "icon_id")
    private Long iconId;

    @Column(name = "menu_title")
    private String title;

    @Column(name = "menu_src_path")
    private String srcPath;

    @Column(name = "menu_depth")
    private int depth = 1;
}
