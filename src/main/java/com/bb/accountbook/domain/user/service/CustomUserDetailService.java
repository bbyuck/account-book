package com.bb.accountbook.domain.user.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.domain.user.repository.UserRepository;
import com.bb.accountbook.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    // 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findWithRolesByEmail(email)
                .map(user -> createUser(email, user))
                .orElseThrow(() -> new GlobalException(ErrorCode.ERR_USR_000));
    }

    private org.springframework.security.core.userdetails.User createUser(String email, User user) {
        if (!user.isActive()) {
            log.error("{} ====== {}", ErrorCode.ERR_USR_002.getValue(), email);
            throw new GlobalException(ErrorCode.ERR_USR_002);
        }

        List<GrantedAuthority> grantedAuthorities = user.getUserRoles()
                .stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getCode().name()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
