package com.bb.accountbook.entity;

import com.bb.accountbook.common.model.status.MailStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tb_mail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "tb_mail_id_generator",
        sequenceName = "seq_mail",
        initialValue = 1, allocationSize = 50)
public class Mail extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "mail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mail_receiver_id")
    private User receiver;

    @Column(name = "mail_ttl")
    private Integer ttl;

    @Column(name = "mail_status")
    @Enumerated(EnumType.STRING)
    private MailStatus status;

    public Mail(User receiver, Integer ttl) {
        this.receiver = receiver;
        this.ttl = ttl;
        this.status = MailStatus.WAIT;
    }
}
