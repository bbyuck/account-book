package com.bb.accountbook.domain.custom.service;

import com.bb.accountbook.common.model.codes.CustomCode;
import com.bb.accountbook.domain.custom.repository.CustomRepository;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.entity.Custom;
import com.bb.accountbook.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomService {
    private final CustomRepository customRepository;
    private final UserService userService;

    public Long saveCustom(String email, CustomCode customCode, String customValue) {
        User user = userService.findUserByEmail(email);
        Custom custom = customRepository.save(new Custom(user, customCode, customValue));
        return custom.getId();
    }

    @Transactional(readOnly = true)
    public List<Custom> findOwnCustoms(String email) {
        return customRepository.findCustomsByOwnerEmail(email);
    }
}
