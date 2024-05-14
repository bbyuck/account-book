package com.bb.accountbook.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "couple")
    private List<UserCouple> userCouples = new ArrayList<>();

    public Couple(String name) {
        this.name = name;
    }

}
