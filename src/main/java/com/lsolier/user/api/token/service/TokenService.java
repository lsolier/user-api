package com.lsolier.user.api.token.service;

import com.lsolier.user.api.session.UserApiStore;
import com.lsolier.user.api.token.model.JwtToken;
import com.lsolier.user.api.token.model.TokenResponse;
import com.lsolier.user.api.token.properties.TokenProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
@EnableConfigurationProperties(TokenProperties.class)
@Slf4j
public class TokenService {

    private final TokenProperties tokenProperties;

    private final UserApiStore userApiStore;

    public TokenService(TokenProperties tokenProperties, UserApiStore userApiStore) {
        this.tokenProperties = tokenProperties;
        this.userApiStore = userApiStore;
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

    public JwtToken readToken(String token) {
        try {
            String subject = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString((this.tokenProperties.getKey().getBytes())))
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            this.userApiStore.saveToken(token);

            return JwtToken.builder().subject(subject).isValid(Boolean.TRUE).build();
        } catch (Exception ex) {
            log.error("Error reading token, Token: {}, Exception: {}", token, ex.getMessage(), ex);
            return JwtToken.builder().subject(Strings.EMPTY).isValid(Boolean.FALSE).build();
        }
    }

}
