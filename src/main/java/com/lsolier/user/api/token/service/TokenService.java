package com.lsolier.user.api.token.service;

import com.lsolier.user.api.token.model.TokenResponse;
import com.lsolier.user.api.token.properties.TokenProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
@EnableConfigurationProperties(TokenProperties.class)
@Slf4j
public class TokenService {

    private final TokenProperties tokenProperties;

    public TokenService(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    public TokenResponse generateToken() {
        String jwt =  Jwts.builder()
                .setSubject(this.tokenProperties.getSubject())
                .setExpiration(new Date(System.currentTimeMillis() + this.tokenProperties.getExpiration()))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString((this.tokenProperties.getKey().getBytes())))
                .compact();

        return TokenResponse.builder()
                .accessToken(jwt)
                .tokenType(this.tokenProperties.getTokenType())
                .expiresIn(new Long(this.tokenProperties.getExpiration()))
                .build();
    }
}
