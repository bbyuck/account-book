package com.bb.accountbook.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tb_ledger_cause")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "tb_ledger_cause_id_generator",
        sequenceName = "seq_ledger_cause",
        initialValue = 1, allocationSize = 50)
public class LedgerCause extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ledger_cause_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ledger_id", nullable = false)
    private Ledger ledger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cause_id", nullable = false)
    private Cause cause;

    public LedgerCause(Ledger ledger, Cause cause) {
        this.ledger = ledger;
        this.cause = cause;
    }
}
