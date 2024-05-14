package com.bb.accountbook.develop;

import com.bb.accountbook.common.model.codes.GenderCode;
import com.bb.accountbook.common.model.codes.RoleCode;
import com.bb.accountbook.domain.user.repository.RoleRepository;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.entity.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final RoleRepository roleRepository;
    private final UserService userService;
    
    @PostConstruct
    public void init() {
        roleRepository.saveAllAndFlush(Arrays.stream(RoleCode.values()).map(Role::new).collect(Collectors.toList()));
        
        String email1 = "k941026h@naver.com";
        String password1 = "pass1";
        userService.join(email1, password1, GenderCode.M);
        
        String email2 = "abc123@naver.com";
        String password2 = "pass2";
        userService.join(email2, password2, GenderCode.W);
    }
    
}
