package com.bb.accountbook.entity;

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

    @Column(name = "user_nickname")
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_family_id")
    private Family family;

    @OneToMany(mappedBy = "owner")
    private List<AccountDetail> accountDetails = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<AccountDetailType> accountDetailTypes = new ArrayList<>();

    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    /**
     * 유저 편의 메서드
     * @param family
     */
    public void joinFamily(Family family) {
        this.family = family;
        family.getUsers().add(this);
    }

}
