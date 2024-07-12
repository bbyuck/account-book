package com.bb.accountbook.domain.icon.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.entity.Icon;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IconServiceTest {

    @Autowired
    IconService iconService;

    @Test
    @DisplayName("아이콘 객체 생성")
    public void insert() throws Exception {
        // given
        String iconName = "credit-card";

        // when
        Long iconId = iconService.insertIcon(iconName);

        // then
        assertThat(iconId).isNotNull();
    }


    @Test
    @DisplayName("아이콘 객체 단건 조회")
    public void singleFind() throws Exception {
        // given
        String name = "credit-card";

        // when
        Long iconId = iconService.insertIcon(name);
        Icon icon = iconService.findIconById(iconId);

        // then
        Assertions.assertThat(icon.getId()).isEqualTo(iconId);
    }


    @Test
    @DisplayName("아이콘 객체 다건 조회")
    public void multiFind() throws Exception {
        // given
        String name1 = "credit-card";
        String name2 = "gift";

        // then
        Assertions.assertThat(iconService.findAllIcons().size()).isEqualTo(30);
    }

    @Test
    @DisplayName("아이콘 객체 수정")
    public void update() throws Exception {
        // given
        String name1 = "credit-card";
        String changeName = "change";
        Long iconId = iconService.insertIcon(name1);

        // when
        iconService.updateIcon(iconId, changeName);

        // then
        Icon icon = iconService.findIconById(iconId);

        Assertions.assertThat(icon.getName()).isEqualTo(changeName);

    }


    @Test
    @DisplayName("아이콘 객체 삭제")
    public void delete() throws Exception {
        // given
        String name = "credit-card";
        Long iconId = iconService.insertIcon(name);

        // when
        iconService.deleteIcon(iconId);

        // then
        assertThrows(GlobalException.class, () -> iconService.findIconById(iconId));
    }

}