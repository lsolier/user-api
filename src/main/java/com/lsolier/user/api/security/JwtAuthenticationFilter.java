package com.lsolier.user.api.security;

import com.lsolier.user.api.token.service.HttpParserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final static String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    private final static String PASSWORD_ANONYMOUS = "PASSWORD_ANONYMOUS";

    private final HttpParserService httpParserService;

    public JwtAuthenticationFilter(HttpParserService httpParserService) {
        this.httpParserService = httpParserService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        RequestMatcher ignoreRequestMatcher = getIgnoreRequestMatcher();
        if (ignoreRequestMatcher.matches(request)) {
            SecurityContextHolder.getContext().setAuthentication(getAnonymousAuthenticationToken(request.getRequestURI()));
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = this.httpParserService.getUserAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private RequestMatcher getIgnoreRequestMatcher() {
        List<RequestMatcher> matchers = new ArrayList<>();
        for (String pattern : SecurityUtils.PERMIT_ALL_PATTERNS) {
            matchers.add(new AntPathRequestMatcher(pattern));
        }
        return new OrRequestMatcher(matchers);
    }

    private AnonymousAuthenticationToken getAnonymousAuthenticationToken(String requestUri) {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority(ROLE_ANONYMOUS));
        User defaultUserDetails = new User(requestUri, requestUri, grantedAuthorityList);
        return new AnonymousAuthenticationToken(PASSWORD_ANONYMOUS, defaultUserDetails, grantedAuthorityList);
    }

}
