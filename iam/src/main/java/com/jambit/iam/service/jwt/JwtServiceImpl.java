package com.jambit.iam.service.jwt;

import com.jambit.iam.conf.security.jwt.JwtClaim;
import com.jambit.iam.controller.rest.model.request.CreateUserTokenRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 12:13 PM
 */
@Service
public class JwtServiceImpl implements JwtService {

    public static final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_TYPE_HEADER = "typ";

    private final String jwtSecret;
    private final Duration validity;

    @Autowired
    public JwtServiceImpl(
            @Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.token.validity}") final Duration validity) {
        this.jwtSecret = jwtSecret;
        this.validity = validity;
    }


    @Override
    public String createJwt(final CreateUserTokenRequest userDetails) {
        final var jti = UUID.randomUUID().toString();
        Date expirationDate = Date.from(Instant.now().plus(validity));
        final Key signingKey = new SecretKeySpec(jwtSecret.getBytes(), algorithm.getJcaName());
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaim.UID.getValue(), userDetails.getInfo().getUserId());
        claims.put(JwtClaim.SUB.getValue(), userDetails.getInfo().getUserId());
        claims.put(JwtClaim.EMAIL.getValue(), userDetails.getInfo().getEmail());
        claims.put(JwtClaim.AUTHORITIES.getValue(), userDetails.getInfo().getRole());
        return Jwts.builder()
                .setId(jti)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .setHeaderParam(TOKEN_TYPE_HEADER, TOKEN_TYPE)
                .addClaims(claims)
                .signWith(algorithm, signingKey)
                .compact();
    }

}
