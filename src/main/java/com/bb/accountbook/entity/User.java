package com.bb.accountbook.entity;

import com.bb.accountbook.common.model.status.UserStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "tb_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "tb_user_id_generator",
        sequenceName = "seq_user",
        initialValue = 1, allocationSize = 50)
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "owner")
    private List<Ledger> ledgers = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<LedgerCategory> ledgerCategories = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private UserCouple userCouple;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Auth auth;

    @OneToMany(mappedBy = "user")
    private List<Custom> customs = new ArrayList<>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.status = UserStatus.ACTIVE;
    }

    public User(String email, String password, UserStatus status) {
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    public void changeStatus(UserStatus status) {
        this.status = status;
    }

    public void changePassword(String password) {
        this.password = password;
    }

}
