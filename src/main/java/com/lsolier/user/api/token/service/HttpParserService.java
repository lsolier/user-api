package com.lsolier.user.api.token.service;

import com.lsolier.user.api.token.model.JwtToken;
import com.lsolier.user.api.token.model.UserAuthentication;
import com.lsolier.user.api.token.utils.TokenUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class HttpParserService {

    private final TokenService tokenService;

    public HttpParserService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public Authentication getUserAuthentication(HttpServletRequest request) {
        String authorizationToken = request.getHeader(TokenUtil.AUTHORIZATION_HEADER);

        if (Objects.isNull(authorizationToken)) {
            return new UserAuthentication(Strings.EMPTY, Boolean.FALSE);
        }

        String accessToken = authorizationToken.replace(TokenUtil.BEARER_PREFIX, Strings.EMPTY);
        JwtToken jwtToken = this.tokenService.readToken(accessToken);
        return new UserAuthentication(jwtToken.getSubject(), jwtToken.isValid());
    }
}
