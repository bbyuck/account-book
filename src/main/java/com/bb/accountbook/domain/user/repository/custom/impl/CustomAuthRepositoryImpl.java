package com.bb.accountbook.domain.user.repository.custom.impl;

import com.bb.accountbook.domain.user.repository.custom.CustomAuthRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomAuthRepositoryImpl implements CustomAuthRepository {

    private final EntityManager em;
}
