package com.bb.accountbook.develop;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"local", "dev", "default"})
@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final TestData testData;

    @PostConstruct
    public void init() {
        testData.init();
        testData.initUsers();
        testData.ledgerServiceTestData();
        testData.customTestData();
    }
    
}
