package com.bb.accountbook.entity;

import com.bb.accountbook.common.model.codes.GenderCode;
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

    @Column(name = "user_gender")
    @Enumerated(EnumType.STRING)
    private GenderCode gender;

    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "refresh_token")
    private String refreshToken;

    @OneToMany(mappedBy = "owner")
    private List<Ledger> ledgers = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<Cause> causes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private UserCouple userCouple;

    public User(String email, String password, GenderCode gender) {
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    public void changeStatus(UserStatus status) {
        this.status = status;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
