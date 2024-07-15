package com.bb.accountbook.entity;

import com.bb.accountbook.common.model.codes.CustomCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Entity
@Table(name = "tb_custom")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "tb_custom_id_generator",
        sequenceName = "seq_custom",
        initialValue = 1, allocationSize = 50)
public class Custom extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "custom_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "custom_code")
    @Enumerated(EnumType.STRING)
    private CustomCode code;

    @Column(name = "custom_value")
    private String value;

    public Custom(User user, CustomCode code, String value) {
        this.user = user;
        this.code = code;
        this.value = value;
    }

    public void changeValue(String value) {
        this.value = value;
    }

}
