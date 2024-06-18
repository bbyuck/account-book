package com.bb.accountbook.domain.user.service;

import com.bb.accountbook.common.exception.GlobalCheckedException;
import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.common.model.codes.RoleCode;
import com.bb.accountbook.common.model.status.MailStatus;
import com.bb.accountbook.common.util.RSACrypto;
import com.bb.accountbook.common.validation.UserValidation;
import com.bb.accountbook.domain.mail.service.MailService;
import com.bb.accountbook.domain.user.dto.TokenDto;
import com.bb.accountbook.domain.user.repository.AuthRepository;
import com.bb.accountbook.domain.user.repository.RoleRepository;
import com.bb.accountbook.domain.user.repository.UserRepository;
import com.bb.accountbook.domain.user.repository.UserRoleRepository;
import com.bb.accountbook.entity.Auth;
import com.bb.accountbook.entity.Mail;
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
import static com.bb.accountbook.common.model.status.UserStatus.ACTIVE;
import static com.bb.accountbook.common.model.status.UserStatus.WAIT;

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

    private final MailService mailService;

    private final RSACrypto rsaCrypto;

    public Long signupWithEmailVerification(String email, String password, String passwordConfirm) {
        // 0. password confirm validation
        if (!password.equals(passwordConfirm)) {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "signup", email, ERR_VALID_004.getValue());
            throw new GlobalException(ERR_VALID_004);
        }

        // 1. 중복 체크
        userRepository.findByEmail(email).ifPresent((user) -> {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "signup", email, ERR_USR_001.getValue());
            throw new GlobalException(ERR_USR_001);
        });

        // 2. User Entity 생성 && insert
        User joinedUser = userRepository.save(new User(email, passwordEncoder.encode(password), WAIT));

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

        try {
            mailService.sendIdentityVerificationEmail(joinedUser, (60 * 15));
        } catch (GlobalCheckedException e) {
            log.error(e.getMessage(), e);
        }

        return joinedUser.getId();
    }

    public Long signup(String email, String password, String passwordConfirm) {
        // 0. password validation
        if (!UserValidation.passwordValidation(password)) {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "signup", email, ERR_VALID_003.getValue());
            throw new GlobalException(ERR_VALID_003);
        }

        if (!UserValidation.passwordConfirmValidation(password, passwordConfirm)) {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "signup", email, ERR_VALID_004.getValue());
            throw new GlobalException(ERR_VALID_004);
        }

        // 1. 중복 체크
        userRepository.findByEmail(email).ifPresent((user) -> {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "signup", email, ERR_USR_001.getValue());
            throw new GlobalException(ERR_USR_001);
        });

        // 2. User Entity 생성 && insert
        User joinedUser = userRepository.save(new User(email, passwordEncoder.encode(password)));

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


    public TokenDto authenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
        Authentication authentication = null;
        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (Exception e) {
            log.error(ERR_AUTH_001.getValue());
            throw new GlobalException(ERR_AUTH_001);
        }

        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
        return tokenProvider.createToken(authentication);
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

    public boolean logout(String email) {
        authRepository
                .findByUserEmail(email)
                .orElseThrow(() -> {
                    log.debug("{}.{}({}): {}", this.getClass().getName(), "logout", email, ERR_AUTH_007.getValue());
                    return new GlobalException(ERR_AUTH_007);
                })
                .reset();
        return true;
    }

    public boolean verifyUser(String target) {
        Long mailId;
        try {
            mailId = Long.valueOf(rsaCrypto.decrypt(target));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GlobalException(ERR_SYS_000);
        }

        Mail mail = mailService.findMailById(mailId);

        // 1. status check
        if (mail.getStatus() != MailStatus.SENT) {
            log.debug("메일의 상태가 SENT 상태가 아님. === mailId : {}", mailId);
            throw new GlobalException(ERR_MAIL_003);
        }
        // 2. ttl check
        if (mail.isExpired()) {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "verifyUser", mail, ERR_MAIL_004.getValue());
            throw new GlobalException(ERR_MAIL_004);
        }

        // 3. user update
        User user = mail.getReceiver();
        user.changeStatus(ACTIVE);

        // 4. mail update
        mail.updateStatus(MailStatus.CONFIRMED);

        return true;
    }

    public boolean changeUserPassword(String email, String password, String newPassword, String newPasswordConfirm) {
        try {
            // 인증 로직만 사용
            authenticate(email, password);
        }
        catch (GlobalException e) {
            log.debug(e.getMessage(), e);
            throw new GlobalException(ERR_USR_003);
        }

        // 0. password validation
        if (!UserValidation.passwordValidation(newPassword)) {
            log.debug("{}.{}({}, {}, {}, {}): {}", this.getClass().getName(), "changeUserPassword", email, password, newPassword, newPasswordConfirm, ERR_VALID_003.getValue());
            throw new GlobalException(ERR_VALID_003);
        }

        if (!UserValidation.passwordConfirmValidation(newPassword, newPasswordConfirm)) {
            log.debug("{}.{}({}, {}, {}, {}): {}", this.getClass().getName(), "changeUserPassword", email, password, newPassword, newPasswordConfirm, ERR_VALID_004.getValue());
            throw new GlobalException(ERR_VALID_004);
        }

        if (password.equals(newPassword)) {
            log.debug("{}.{}({}, {}, {}, {}): {}", this.getClass().getName(), "changeUserPassword", email, password, newPassword, newPasswordConfirm, ERR_VALID_004.getValue());
            throw new GlobalException(ERR_USR_004);
        }

        User user = findUserByEmail(email);
        user.changePassword(passwordEncoder.encode(newPassword));

        // logout 처리
        logout(email);

        return true;
    }

    public void updateAuth(String email, TokenDto token, boolean autoLogin) {
        token.setAutoLogin(autoLogin);

        // token info save if not exist / update if exist
        authRepository.findByUserEmail(email)
                .ifPresentOrElse(
                        auth -> auth.update(token.getRefreshToken(), token.isAutoLogin())
                        , () -> saveAuthInfo(findUserByEmail(email), token));
    }
}
