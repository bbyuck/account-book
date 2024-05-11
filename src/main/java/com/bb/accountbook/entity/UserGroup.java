package com.bb.accountbook.entity;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.GroupCode;
import com.bb.accountbook.common.model.codes.MemberCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.bb.accountbook.common.model.codes.ErrorCode.ERR_GRP_000;

@Entity
@Getter
@Slf4j
@Table(name = "tb_user_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserGroup {

    @Id @GeneratedValue
    @Column(name = "user_group")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "nickname", length = 20)
    private String nickname;

    @Column(name = "member_code", length = 20)
    private MemberCode memberCode;

    public UserGroup(User user, Group group, String nickname, MemberCode memberCode) {
        this.user = user;
        this.group = group;
        this.nickname = nickname;

        if (!group.getGroupCode().getMemberCodes().contains(memberCode)) {
            log.error(ERR_GRP_000.getValue());
            throw new GlobalException(ERR_GRP_000);
        }

        this.memberCode = memberCode;
    }
}
