package com.bb.accountbook.domain.custom.repository.custom.impl;

import com.bb.accountbook.common.model.codes.CustomCode;
import com.bb.accountbook.domain.custom.repository.custom.CustomCustomRepository;
import com.bb.accountbook.entity.Custom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomCustomRepositoryImpl implements CustomCustomRepository {

    private final EntityManager em;

    @Override
    public List<Custom> findCustomsByOwnerEmail(String email) {
        String jpql = "select c " +
                "from Custom c " +
                "join fetch User u " +
                "on c.user = u " +
                "where u.email = :email";
        return em.createQuery(jpql, Custom.class)
                .setParameter("email", email)
                .getResultList();
    }

    @Override
    public Optional<Custom> findCustomByOwnerEmailAndCode(String email, CustomCode code) {
        String jpql = "select c " +
                "from Custom c " +
                "join fetch User u " +
                "on c.user = u " +
                "where u.email = :email " +
                "and c.code = :code";
        return em.createQuery(jpql, Custom.class)
                .setParameter("email", email)
                .setParameter("code", code)
                .getResultList().stream().findFirst();
    }
}
