package com.bb.accountbook.entity;

import com.bb.accountbook.common.model.codes.LedgerCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.ConstraintMode.NO_CONSTRAINT;

@Entity
@Getter
@Table(name = "tb_ledger_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "tb_ledger_category_id_generator",
        sequenceName = "seq_ledger_category",
        initialValue = 1, allocationSize = 50)
public class LedgerCategory extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "ledger_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "icon_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
    private Icon icon;

    @Column(name = "ledger_category_name", length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "ledger_code", length = 4, nullable = false)
    private LedgerCode ledgerCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    public LedgerCategory(User user, String name, LedgerCode ledgerCode) {
        this.name = name;
        user.getLedgerCategories().add(this);

        this.owner = user;
        this.ledgerCode = ledgerCode;
    }

    public LedgerCategory(User user, String name, LedgerCode ledgerCode, Icon icon) {
        this.name = name;
        user.getLedgerCategories().add(this);

        this.owner = user;
        this.ledgerCode = ledgerCode;

        this.icon = icon;
    }

    public void update(String name, LedgerCode ledgerCode, Icon icon) {
        this.name = name;
        this.ledgerCode = ledgerCode;
        this.icon = icon;
    }
}
