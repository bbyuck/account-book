package com.bb.accountbook.domain.group.repository;

import com.bb.accountbook.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.bb.accountbook.entity.QUserGroup.userGroup;

@Repository
@RequiredArgsConstructor
public class GroupCustomRepositoryImpl implements GroupCustomRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    @Override
    public UserGroup saveUserGroup(UserGroup userGroup) {
        em.persist(userGroup);
        return userGroup;
    }

    @Override
    public UserGroup findUserGroup(Long userGroupId) {
        return em.find(UserGroup.class, userGroupId);
    }

    @Override
    public boolean isExistUserGroup(User user, Group group) {
        Long count = queryFactory
                .select(userGroup.count())
                .from(userGroup)
                .join(QUser.user, userGroup.user)
                .fetchJoin()
                .join(QGroup.group, userGroup.group)
                .fetchJoin()
                .where(
                        userGroup.user.eq(user),
                        userGroup.group.eq(group)
                ).fetchOne();
        count = count == null ? 0L : count;
        return count > 0;
    }
}
