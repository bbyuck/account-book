package com.bb.accountbook.entity;

import com.bb.accountbook.common.model.codes.LedgerCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tb_cause")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "tb_cause_id_generator",
        sequenceName = "seq_cause",
        initialValue = 1, allocationSize = 50)
public class Cause extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "cause_id")
    private Long id;

    @Column(name = "cause_name", length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "ledger_code", length = 4, nullable = false)
    private LedgerCode ledgerCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    public Cause(String name, User user, LedgerCode ledgerCode) {
        this.name = name;
        user.getCauses().add(this);

        this.owner = user;
        this.ledgerCode = ledgerCode;
    }
}
