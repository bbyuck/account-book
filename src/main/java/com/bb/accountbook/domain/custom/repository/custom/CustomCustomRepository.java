package com.bb.accountbook.domain.custom.repository.custom;

import com.bb.accountbook.common.model.codes.CustomCode;
import com.bb.accountbook.entity.Custom;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomCustomRepository {

    List<Custom> findCustomsByOwnerEmail(String email);

    Optional<Custom> findCustomByOwnerEmailAndCode(String email, CustomCode code);
}
