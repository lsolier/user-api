package com.lsolier.user.api.security;

import com.lsolier.user.api.token.service.HttpParserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final HttpParserService httpParserService;

    public WebSecurityConfig(HttpParserService httpParserService) {
        this.httpParserService = httpParserService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                    .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getOutputStream().println("{ \"message\": \"" + "Unauthorised user" + "\" }");
                        }
                );

        http.csrf().disable().cors()
                .and()
                .authorizeRequests()
                .antMatchers(SecurityUtils.PERMIT_ALL_PATTERNS).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(this.httpParserService), UsernamePasswordAuthenticationFilter.class);

        http.headers().frameOptions().disable();
    }

}

