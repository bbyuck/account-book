package com.bb.accountbook.domain.user.repository;

import com.bb.accountbook.domain.user.repository.custom.UserCustomRepository;
import com.bb.accountbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
    Optional<User> findByEmail(String email);
}
