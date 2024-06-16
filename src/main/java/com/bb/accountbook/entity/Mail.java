package com.bb.accountbook.entity;

import com.bb.accountbook.common.model.status.MailStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mail_receiver_id")
    private User receiver;

    @Column(name = "mail_ttl")
    private Integer ttl;

    @Column(name = "mail_status")
    @Enumerated(EnumType.STRING)
    private MailStatus status;

    @Column(name = "mail_sent_date_time")
    private LocalDateTime sentDateTime;

    public Mail(User receiver, Integer ttl) {
        this.receiver = receiver;
        this.ttl = ttl;
        this.status = MailStatus.WAIT;
    }

    public void changeStatusToSent() {
        this.status = MailStatus.SENT;
        this.sentDateTime = LocalDateTime.now();
    }

    public boolean isExpired() {
        LocalDateTime expiryTime = sentDateTime.plus(ttl, ChronoUnit.SECONDS);
        LocalDateTime now = LocalDateTime.now();
        return expiryTime.isAfter(now);
    }

    public void updateStatus(MailStatus status) {
        this.status = status;
    }
}
