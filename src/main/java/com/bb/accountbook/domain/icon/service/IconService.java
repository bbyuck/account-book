package com.bb.accountbook.domain.icon.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.domain.icon.repository.IconRepository;
import com.bb.accountbook.entity.Icon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bb.accountbook.common.model.codes.ErrorCode.ERR_ICO_000;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IconService {

    private final IconRepository iconRepository;

    @Transactional(readOnly = true)
    public Icon findIconById(Long id) {
        return iconRepository.findById(id).orElseThrow(() -> {
            log.debug("{} ====== {}", ERR_ICO_000.getValue(), id);
            return new GlobalException(ERR_ICO_000);
        });
    }

}