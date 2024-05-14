package com.bb.accountbook.domain.group.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.common.model.codes.GroupCode;
import com.bb.accountbook.common.model.codes.MemberCode;
import com.bb.accountbook.domain.group.repository.GroupRepository;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.entity.Group;
import com.bb.accountbook.entity.User;
import com.bb.accountbook.entity.UserGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.atn.ErrorInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {

    private final GroupRepository groupRepository;

    private final UserService userService;

    public Long joinGroup(Long userId, Long groupId, String nickname, MemberCode memberCode) {
        User findUser = userService.findUserById(userId);
        Group findGroup = this.findGroup(groupId);

        if (groupRepository.isExistUserGroup(findUser, findGroup)) {
            log.error(ErrorCode.ERR_GRP_002.getValue());
            throw new GlobalException(ErrorCode.ERR_GRP_002);
        }

        UserGroup savedUserGroup = groupRepository.saveUserGroup(new UserGroup(findUser, findGroup, nickname, memberCode));

        return savedUserGroup.getId();
    }

    public Long makeGroup(String groupName, GroupCode groupCode) {
        Group savedGroup = groupRepository.save(new Group(groupName, groupCode));
        return savedGroup.getId();
    }

    @Transactional(readOnly = true)
    public Group findGroup(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> {
            log.error(ErrorCode.ERR_GRP_000.getValue());
            return new GlobalException(ErrorCode.ERR_GRP_001);
        });
    }

    public Long connectToOpponent(Long apiCallerId, String opponentEmail, String nickname, MemberCode memberCode) {
        // 상대방 정보 get
        User opponent = userService.findUserByEmail(opponentEmail);
        MemberCode opponentMemberCode = memberCode == MemberCode.HUSBAND ? MemberCode.WIFE : MemberCode.HUSBAND;

        // 새 그룹 생성
        Long newGroupId = makeGroup("부부", GroupCode.MARRIED_COUPLE);

        UserGroup callersUserGroup = groupRepository.findUserGroup(joinGroup(apiCallerId, newGroupId, nickname, memberCode));

        // 상대방에게 알림 전송
        joinGroup(opponent.getId(), newGroupId, null, opponentMemberCode);

        return callersUserGroup.getId();
    }
}
