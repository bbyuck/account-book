package com.bb.accountbook.domain.user.repository.custom;

import com.bb.accountbook.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCustomRepository {
    Optional<User> findWithRolesByEmail(String email);
    Optional<User> findWithRolesById(Long userId);
}
