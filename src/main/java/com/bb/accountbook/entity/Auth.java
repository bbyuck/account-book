package com.bb.accountbook.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tb_auth")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "tb_auth_id_generator",
        sequenceName = "seq_auth",
        initialValue = 1, allocationSize = 50)
public class Auth extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "auth_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "auto_login")
    private boolean autoLogin = false;


    public Auth(User user, String refreshToken, boolean autoLogin) {
        this.user = user;
        this.refreshToken = refreshToken;
        this.autoLogin = autoLogin;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void update(String refreshToken, boolean autoLogin) {
        this.refreshToken = refreshToken;
        this.autoLogin = autoLogin;
    }

}
