package com.bb.accountbook.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tb_account_detail_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountDetailType extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "account_detail_type_id")
    private Long id;

    @Column(name = "account_detail_type_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    public AccountDetailType(String name, User user) {
        this.name = name;
        user.getAccountDetailTypes().add(this);
        this.owner = user;
    }
}
