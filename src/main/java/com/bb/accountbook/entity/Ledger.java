package com.bb.accountbook.entity;

import com.bb.accountbook.common.model.codes.LedgerCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.ConstraintMode.*;

@Entity
@Getter
@Table(name = "tb_ledger")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "tb_ledger_id_generator",
        sequenceName = "seq_ledger",
        initialValue = 1, allocationSize = 50)
public class Ledger extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ledger_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ledger_category_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
    private LedgerCategory ledgerCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "ledger_code", nullable = false)
    private LedgerCode code;

    @Column(name = "ledger_date", nullable = false)
    private LocalDate date;

    @Column(name = "ledger_amount", nullable = false)
    private Long amount = 0L;

    @Column(name = "ledger_description", length = 100)
    private String description;


    public Ledger(User user, LedgerCode code, LocalDate date, Long amount, String description) {
        this.owner = user;
        this.code = code;
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public Ledger(LedgerCategory ledgerCategory, User user, LedgerCode code, LocalDate date, Long amount, String description) {
        this(user, code, date, amount, description);
        this.ledgerCategory = ledgerCategory;
    }

    public void update(LedgerCode code, LocalDate date, Long amount, String description) {
        this.code = code;
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

}
