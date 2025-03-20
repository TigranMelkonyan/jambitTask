package com.jambit.infrastructure.config.security;

import com.jambit.infrastructure.config.security.jwt.JwtTokenInspector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 6:28 PM
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String JWT_ALGORITHM = "HmacSHA256";

    private final String jwtSecret;
    private final ApplicationAuthEntryPoint authenticationEntryPoint;

    public SecurityConfig(
            @Value("${jwt.secret}") String jwtSecret,
            final ApplicationAuthEntryPoint authenticationEntryPoint) {
        this.jwtSecret = jwtSecret;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .authenticationEntryPoint(authenticationEntryPoint)
                .opaqueToken()
                .introspector(new JwtTokenInspector(jwtDecoder()));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey key = new SecretKeySpec(jwtSecret.getBytes(), JWT_ALGORITHM);
        return NimbusJwtDecoder.withSecretKey(key).build();
    }

}
