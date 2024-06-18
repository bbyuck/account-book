package com.bb.accountbook.domain.custom.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.CustomCode;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.domain.custom.repository.CustomRepository;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.entity.Custom;
import com.bb.accountbook.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bb.accountbook.common.model.codes.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomService {
    private final CustomRepository customRepository;
    private final UserService userService;
    private final String DEFAULT_COLOR = "d1fbff";

    public Long saveCustom(String email, CustomCode customCode, String customValue) {
        Custom custom = customRepository.findCustomByOwnerEmailAndCode(email, customCode)
                .orElseGet(() -> {
                    User user = userService.findUserByEmail(email);
                    return customRepository.save(new Custom(user, customCode, customValue));
                });
        custom.changeValue(customValue);

        return custom.getId();
    }

    @Transactional(readOnly = true)
    public List<Custom> findOwnCustoms(String email) {
        return customRepository.findCustomsByOwnerEmail(email);
    }

    @Transactional(readOnly = true)
    public Custom findCustom(String email, CustomCode code) {
        return customRepository.findCustomByOwnerEmailAndCode(email, code).orElseThrow(() -> {
            log.debug("{}.{}({}, {}): {}", this.getClass().getName(), "findCustom", email, code, ERR_CUS_000.getValue());
            return new GlobalException(ERR_CUS_000);
        });
    }

    public String getCustomColor(String email) {
        try {
            Custom custom = findCustom(email, CustomCode.COLOR);
            return custom.getValue();
        } catch (GlobalException ge) {
            return DEFAULT_COLOR;
        }
    }
}
