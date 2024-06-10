package com.bb.accountbook.domain.custom.service;

import com.bb.accountbook.common.model.codes.CustomCode;
import com.bb.accountbook.entity.Custom;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomServiceTest {

    @Autowired
    CustomService customService;

    @Test
    @DisplayName("유저별 커스터마이즈 Entity 저장")
    public void saveCustom() throws Exception {
        // given
        String userEmail = "abc123@naver.com";
        CustomCode code = CustomCode.COLOR;
        String value = "98DF96";

        // when
        Long savedCustomId = customService.saveCustom(userEmail, code, value);

        // then
        Assertions.assertThat(savedCustomId).isNotNull();

    }


    @Test
    @DisplayName("유저별 커스터마이즈 조회")
    public void findOwnCustoms() throws Exception {
        // given
        String userEmail =  "abc123@naver.com";
        CustomCode code = CustomCode.COLOR;
        String value = "98DF96";

        customService.saveCustom(userEmail, code, value);
        // when

        List<Custom> ownCustoms = customService.findOwnCustoms(userEmail);

        // then
        Assertions.assertThat(ownCustoms.get(0).getValue()).isEqualTo("98DF96");
    }
}