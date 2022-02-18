package com.lsolier.user.api.security;

import com.lsolier.user.api.token.service.HttpParserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationFilter extends GenericFilterBean {

    private final HttpParserService httpParserService;

    public JwtAuthenticationFilter(HttpParserService httpParserService) {
        this.httpParserService = httpParserService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = this.httpParserService.getUserAuthentication((HttpServletRequest)request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
