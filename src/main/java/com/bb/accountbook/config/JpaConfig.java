package com.bb.accountbook.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfig {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("account-book");

    @Bean
    public EntityManager em() {
        return emf.createEntityManager();
    }
}
