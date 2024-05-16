package com.bb.accountbook.security;


import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.domain.user.dto.AdditionalPayloadDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityContextProvider {

    public static String getCurrentEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug(ErrorCode.ERR_AUTH_000.getValue());
            throw new GlobalException(ErrorCode.ERR_AUTH_000);
        }

        String email = null;
        if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            email = springSecurityUser.getUsername();
        }
        else if(authentication.getPrincipal() instanceof String) {
            email = (String) authentication.getPrincipal();
        }


        if (email == null) {
            log.debug(ErrorCode.ERR_AUTH_000.getValue());
            throw new GlobalException(ErrorCode.ERR_AUTH_000);
        }

        return email;
    }

    public static Long getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug(ErrorCode.ERR_AUTH_000.getValue());
            throw new GlobalException(ErrorCode.ERR_AUTH_000);
        }

        Long userId = null;
        if (authentication.getDetails() instanceof AdditionalPayloadDto additionalPayloadDto) {
            userId = additionalPayloadDto.getUid();
        }

        if (userId == null) {
            log.debug(ErrorCode.ERR_AUTH_000.getValue());
            throw new GlobalException(ErrorCode.ERR_AUTH_000);
        }

        return userId;
    }
}
