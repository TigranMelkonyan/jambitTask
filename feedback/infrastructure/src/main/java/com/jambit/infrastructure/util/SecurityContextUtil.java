package com.jambit.infrastructure.util;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 10:08 PM
 */
public final class SecurityContextUtil {

    private SecurityContextUtil() {
    }

    @Bean
    @RequestScope
    public static UUID currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) || !(authentication.getPrincipal() instanceof OAuth2AuthenticatedPrincipal)) {
            return null;
        }
        OAuth2AuthenticatedPrincipal oAuth2Principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        String userId = oAuth2Principal.getAttribute("uid");
        if (userId != null) {
            return UUID.fromString(userId);
        }
        return null;
    }
}
