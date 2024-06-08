package com.bb.accountbook.domain.custom.repository.custom.impl;

import com.bb.accountbook.domain.custom.repository.custom.CustomCustomRepository;
import com.bb.accountbook.entity.Custom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
