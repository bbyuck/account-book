package com.bb.accountbook.domain.couple.repository;

import com.bb.accountbook.domain.couple.repository.custom.CoupleCustomRepository;
import com.bb.accountbook.entity.Couple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoupleRepository extends JpaRepository<Couple, Long>, CoupleCustomRepository {
}
