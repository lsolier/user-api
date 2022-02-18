package com.lsolier.user.api.token.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class JwtToken {

    private String subject;

    private boolean isValid;

}
