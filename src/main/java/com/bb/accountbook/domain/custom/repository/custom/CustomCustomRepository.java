package com.bb.accountbook.domain.custom.repository.custom;

import com.bb.accountbook.entity.Custom;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomCustomRepository {

    List<Custom> findCustomsByOwnerEmail(String email);
}
