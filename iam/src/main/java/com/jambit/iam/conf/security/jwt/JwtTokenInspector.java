package com.jambit.iam.conf.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 11:28 AM
 */
@Component
public class JwtTokenInspector implements OpaqueTokenIntrospector {

    private final JwtDecoder jwtDecoder;

    public JwtTokenInspector(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            checkJwtClaims(jwt);

            List<GrantedAuthority> authorities = new ArrayList<>();

            if (jwt.getClaim("authorities") != null) {
                String role = jwt.getClaim("authorities");
                authorities.add(new SimpleGrantedAuthority(role));
            }
            if (jwt.getClaim("authorities") != null) {
                List<String> roles = jwt.getClaim("authorities");
                for (String role : roles) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                }
            }
            return new DefaultOAuth2AuthenticatedPrincipal(
                    jwt.getSubject(),
                    jwt.getClaims(),
                    authorities
            );
        } catch (JwtException e) {
            throw new OAuth2IntrospectionException("Invalid JWT token", e);
        }
    }

    private void checkJwtClaims(Jwt jwt) {
        List<String> requiredClaims = Arrays.asList(
                JwtClaim.UID.getValue(),
                JwtClaim.SUB.getValue(),
                JwtClaim.EXPIRATION.getValue()
        );
        if (!jwt.getClaims().keySet().containsAll(requiredClaims)) {
            throw new OAuth2IntrospectionException("JWT token doesn't contain all required claims");
        }
    }
}
