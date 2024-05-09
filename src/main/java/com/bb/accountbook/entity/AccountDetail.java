package com.bb.accountbook.entity;

import com.bb.accountbook.common.model.codes.AccountDetailCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "tb_account_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountDetail extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "account_detail_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_detail_code")
    private AccountDetailCode code;

    @Column(name = "account_detail_date")
    private LocalDate date;

    @Column(name = "account_detail_amount")
    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    public AccountDetail(AccountDetailCode code, Long amount, User user) {
        this.code = code;
        this.date = LocalDate.now();
        this.amount = amount;
        user.getAccountDetails().add(this);
        this.owner = user;
    }


}
