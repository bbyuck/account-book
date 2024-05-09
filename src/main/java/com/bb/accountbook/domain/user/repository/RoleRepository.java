package com.bb.accountbook.domain.user.repository;

import com.bb.accountbook.common.model.codes.RoleCode;
import com.bb.accountbook.domain.user.repository.custom.RoleCustomRepository;
import com.bb.accountbook.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, RoleCustomRepository {
    Optional<Role> findByName(RoleCode name);
}
