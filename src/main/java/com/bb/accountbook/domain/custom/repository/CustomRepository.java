package com.bb.accountbook.domain.custom.repository;

import com.bb.accountbook.domain.custom.repository.custom.CustomCustomRepository;
import com.bb.accountbook.entity.Custom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomRepository extends JpaRepository<Custom, Long>, CustomCustomRepository {
}
