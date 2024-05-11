package com.bb.accountbook.entity;

import com.bb.accountbook.common.model.codes.LedgerCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "tb_ledger")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ledger extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ledger_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "ledger_code", nullable = false)
    private LedgerCode code;

    @Column(name = "ledger_date", nullable = false)
    private LocalDate date;

    @Column(name = "ledger_amount", nullable = false)
    private Long amount = 0L;

    @Column(name = "ledger_contents", length = 100)
    private String contents;

    public Ledger(User user, LedgerCode code, LocalDate date, Long amount, String contents) {
        this.owner = user;
        this.code = code;
        this.date = date;
        this.amount = amount;
        this.contents = contents;
    }


}
