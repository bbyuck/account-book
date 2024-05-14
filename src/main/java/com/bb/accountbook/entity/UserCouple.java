package com.bb.accountbook.entity;

import com.bb.accountbook.common.model.status.UserCoupleStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Slf4j
@Table(name = "tb_user_couple")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCouple {

    @Id @GeneratedValue
    @Column(name = "user_couple_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id")
    private Couple couple;

    @Column(name = "nickname", length = 20)
    private String nickname;

    @Column(name = "user_couple_status")
    @Enumerated(EnumType.STRING)
    private UserCoupleStatus status = UserCoupleStatus.WAIT;

    public UserCouple(User user, Couple couple, String nickname) {
        this.user = user;
        this.couple = couple;
        this.nickname = nickname;
    }

    public void changeStatus(UserCoupleStatus status) {
        this.status = status;
    }
}
