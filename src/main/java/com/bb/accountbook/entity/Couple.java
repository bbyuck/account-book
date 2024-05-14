package com.bb.accountbook.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tb_couple")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Couple {

    @Id
    @GeneratedValue
    @Column(name = "couple_id")
    private Long id;

    @Column(name = "couple_name")
    private String name;

    public Couple(String name) {
        this.name = name;
    }

}
