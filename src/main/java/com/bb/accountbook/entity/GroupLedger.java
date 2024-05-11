package com.bb.accountbook.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tb_group_ledger")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupLedger extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "group_ledger_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ledger_id")
    private Ledger ledger;

    public GroupLedger(Group group, Ledger ledger) {
        this.group = group;
        this.ledger = ledger;
    }
}
