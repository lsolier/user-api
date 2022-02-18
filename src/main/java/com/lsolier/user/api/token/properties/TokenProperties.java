package com.lsolier.user.api.token.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "token.jwt")
@Getter
@Setter
public class TokenProperties {

    @Value("${token.type}")
    private String tokenType;

    private String subject;

    private Integer expiration;

    private String key;

}
