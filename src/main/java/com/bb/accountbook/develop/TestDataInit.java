package com.bb.accountbook.develop;

import com.bb.accountbook.common.model.codes.GenderCode;
import com.bb.accountbook.common.model.codes.RoleCode;
import com.bb.accountbook.domain.couple.repository.CoupleRepository;
import com.bb.accountbook.domain.user.repository.RoleRepository;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.entity.Couple;
import com.bb.accountbook.entity.Role;
import com.bb.accountbook.entity.User;
import com.bb.accountbook.entity.UserCouple;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final TestData testData;

    @PostConstruct
    public void init() {
        testData.init();
        testData.ledgerServiceTestData();
    }
    
}
