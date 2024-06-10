package com.bb.accountbook.domain.user.repository;

import com.bb.accountbook.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByRefreshToken(String refreshToken);
    Optional<Auth> findByUserId(Long userId);
}
