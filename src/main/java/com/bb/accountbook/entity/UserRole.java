package com.bb.accountbook.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tb_user_role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRole extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "user_role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }
}
