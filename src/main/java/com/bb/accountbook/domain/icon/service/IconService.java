package com.bb.accountbook.domain.icon.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.domain.icon.repository.IconRepository;
import com.bb.accountbook.entity.Icon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<Icon> findAllIcons() {
        return iconRepository.findAll();
    }

    public Long insertIcon(String name) {
        return iconRepository.save(new Icon(name)).getId();
    }

    public Long updateIcon(Long id, String name) {
        Icon icon = findIconById(id);
        icon.change(name);

        return icon.getId();
    }

    public void deleteIcon(Long id) {
        iconRepository.deleteById(id);
    }

}
