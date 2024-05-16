package com.bb.accountbook.domain.user.repository.custom.impl;

import com.bb.accountbook.domain.user.repository.custom.UserCustomRepository;
import com.bb.accountbook.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final EntityManager em;

    @Override
    public Optional<User> findWithRolesByEmail(String email) {
        String jpql = "select u " +
                "from User u " +
                "join fetch UserRole ur " +
                "on ur.user = u " +
                "join fetch Role r " +
                "on ur.role = r " +
                "where u.email = :email";

        return em.createQuery(jpql, User.class)
                .setParameter("email", email)
                .getResultList().stream().findFirst();
    }

    @Override
    public Optional<User> findWithRolesById(Long userId) {
        String jpql = "select u " +
                "from User u " +
                "join fetch UserRole ur " +
                "on ur.user = u " +
                "join fetch Role r " +
                "on ur.role = r " +
                "where u.id = :userId";
        return em.createQuery(jpql, User.class)
                .setParameter("userId", userId)
                .getResultList().stream().findFirst();
    }
}
