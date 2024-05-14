package com.bb.accountbook.entity;

import com.bb.accountbook.common.model.codes.GenderCode;
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

    @OneToMany(mappedBy = "owner")
    private List<Ledger> ledgers = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<Cause> causes = new ArrayList<>();

    public User(String email, String password, GenderCode gender) {
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

}
