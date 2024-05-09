package com.bb.accountbook.domain.user.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.RoleCode;
import com.bb.accountbook.domain.user.repository.RoleRepository;
import com.bb.accountbook.domain.user.repository.UserRepository;
import com.bb.accountbook.domain.user.repository.UserRoleRepository;
import com.bb.accountbook.entity.User;
import com.bb.accountbook.entity.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bb.accountbook.common.model.codes.CommonCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserRoleRepository userRoleRepository;

    public Long join(String email, String password) {
        // 1. 중복 체크
        userRepository.findByEmail(email).ifPresent((user) -> {
            log.debug("{} ====== {}", ERR_USER_001.getValue(), user.getEmail());
            throw new GlobalException(ERR_USER_001);
        });

        // 2. User Entity 생성 && insert
        User joinedUser = userRepository.save(new User(email, password));

        // 3. default Role Entity 생성 및 UserRole Entity mapping
        List<UserRole> newUserRoles = RoleCode.DEFAULT
                .stream()
                .map(roleCode ->
                        new UserRole(joinedUser, roleRepository.findByName(roleCode)
                                .orElseThrow(() -> {
                                    log.error("Role Entity를 찾을 수 없습니다. ====== {}", roleCode.name());
                                    return new GlobalException(ERR_SYS_000);
                                }))
                ).toList();
        userRoleRepository.saveAll(newUserRoles);

        // 4. 정상 처리 후 메일 발송ㅍ -> 메세지 큐로 구현

        return joinedUser.getId();
    }

}
