package com.bb.accountbook.develop;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
