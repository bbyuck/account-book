package com.bb.accountbook.domain.user.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.GenderCode;
import com.bb.accountbook.common.model.codes.RoleCode;
import com.bb.accountbook.domain.user.dto.TokenDto;
import com.bb.accountbook.domain.user.repository.AuthRepository;
import com.bb.accountbook.domain.user.repository.RoleRepository;
import com.bb.accountbook.domain.user.repository.UserRepository;
import com.bb.accountbook.domain.user.repository.UserRoleRepository;
import com.bb.accountbook.entity.Auth;
import com.bb.accountbook.entity.User;
import com.bb.accountbook.entity.UserRole;
import com.bb.accountbook.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bb.accountbook.common.model.codes.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final AuthRepository authRepository;


    public Long signup(String email, String password, GenderCode gender) {
        // 1. 중복 체크
        userRepository.findByEmail(email).ifPresent((user) -> {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "signup", email, ERR_USR_001.getValue());
            throw new GlobalException(ERR_USR_001);
        });

        // 2. User Entity 생성 && insert
        User joinedUser = userRepository.save(new User(email, passwordEncoder.encode(password), gender));

        // 3. default Role Entity 생성 및 UserRole Entity mapping
        List<UserRole> newUserRoles = RoleCode.DEFAULT
                .stream()
                .map(roleCode ->
                        new UserRole(joinedUser, roleRepository.findByCode(roleCode)
                                .orElseThrow(() -> {
                                    log.debug("{}.{}({}): {}", this.getClass().getName(), "signup", email, "Role Entity를 찾을 수 없습니다.");
                                    return new GlobalException(ERR_SYS_000);
                                }))
                ).toList();
        userRoleRepository.saveAll(newUserRoles);

        // TODO 4. 정상 처리 후 메일 발송 -> 메세지 큐로 구현

        return joinedUser.getId();
    }

    @Transactional(readOnly = true)
    public User findUserById(Long userId) {
        return userRepository.findWithRolesById(userId).orElseThrow(() -> {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "findUserById", userId, ERR_USR_000.getValue());
            return new GlobalException(ERR_USR_000);
        });
    }

    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findWithRolesByEmail(email).orElseThrow(() -> {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "findUserByEmail", email, ERR_USR_000.getValue());
            return new GlobalException(ERR_USR_000);
        });
    }

    public Long saveAuthInfo(User user, TokenDto token) {
        Auth auth = authRepository.save(new Auth(user, token.getRefreshToken(), token.isAutoLogin()));
        return auth.getId();
    }

    public TokenDto authenticate(String email, String password, boolean autoLogin) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        User user = findUserByEmail(email);

        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
        Authentication authentication = null;
        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (Exception e) {
            log.error(ERR_AUTH_001.getValue());
            throw new GlobalException(ERR_AUTH_001);
        }

        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
        TokenDto token = tokenProvider.createToken(authentication);
        token.setAutoLogin(autoLogin);

        // token info save if not exist / update if exist
        authRepository.findByUserId(user.getId())
                .ifPresentOrElse(auth -> auth.update(token.getRefreshToken()
                        , token.isAutoLogin()), () -> saveAuthInfo(user, token));

        return token;
    }

    public TokenDto reissueToken(String refreshToken) {
        tokenProvider.validate(refreshToken);

        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User apiCaller = userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "reissueToken", refreshToken, ERR_USR_000.getValue());
            // 유저를 찾을 수 없음
            return new GlobalException(ERR_USR_000);
        });

        Auth auth = apiCaller.getAuth();
        if (auth == null || !auth.getRefreshToken().equals(refreshToken)) {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "reissueToken", refreshToken, ERR_AUTH_003.getValue());
            // 인증에 실패
            throw new GlobalException(ERR_AUTH_003);
        }

        TokenDto token = tokenProvider.createToken(authentication);
        auth.updateRefreshToken(token.getRefreshToken());

        return token;
    }

}
