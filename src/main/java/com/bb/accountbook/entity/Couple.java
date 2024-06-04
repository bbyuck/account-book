package com.bb.accountbook.entity;

import com.bb.accountbook.common.model.status.CoupleStatus;
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
@SequenceGenerator(
        name = "tb_couple_id_generator",
        sequenceName = "seq_couple",
        initialValue = 1, allocationSize = 50)
public class Couple {

    @Id
    @GeneratedValue
    @Column(name = "couple_id")
    private Long id;

    @Column(name = "couple_name")
    private String name;

    @Column(name = "couple_status")
    private CoupleStatus status;

    @OneToMany(mappedBy = "couple")
    private List<UserCouple> userCouples = new ArrayList<>();

    public Couple(String name) {
        this.name = name;
        this.status = CoupleStatus.INACTIVE;
    }

    public void changeStatus(CoupleStatus status) {
        this.status = status;
    }
}
