package com.bb.accountbook.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "tb_family")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Family {

    @Id @GeneratedValue
    @Column(name = "family_id")
    private Long id;

    @Column(name = "family_name")
    private String name;

    @OneToMany(mappedBy = "family")
    private List<User> users = new ArrayList<>();

    public Family(String name) {
        this.name = name;
    }
}
