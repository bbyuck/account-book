package com.bb.accountbook.domain.group.repository;

import com.bb.accountbook.entity.Group;
import com.bb.accountbook.entity.User;
import com.bb.accountbook.entity.UserGroup;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupCustomRepository {
    UserGroup saveUserGroup(UserGroup userGroup);
    UserGroup findUserGroup(Long userGroupId);
    boolean isExistUserGroup(User user, Group group);

}
