package com.bb.accountbook.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tb_couple_ledger")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoupleLedger extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "couple_ledger_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id")
    private Couple couple;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ledger_id")
    private Ledger ledger;

    public CoupleLedger(Couple couple, Ledger ledger) {
        this.couple = couple;
        this.ledger = ledger;
    }
}
