package com.lsolier.user.api.security;

import org.apache.commons.lang3.ArrayUtils;

public final class SecurityUtils {

    private SecurityUtils() {
        throw new UnsupportedOperationException();
    }

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String BEARER_PREFIX = "Bearer ";

    public static final String TOKEN_ENDPOINT = "/tokens";

    public static final String H2_ENDPOINTS = "/h2-console/**";

    public static final String ACTUATOR_ENDPOINTS = "/actuator/**";

    private static final String[] SWAGGER_ENDPOINTS = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    public static final String[] COMMON_PATTERNS = new String[] { TOKEN_ENDPOINT, H2_ENDPOINTS, ACTUATOR_ENDPOINTS };

    public static final String[] PERMIT_ALL_PATTERNS = ArrayUtils.addAll(COMMON_PATTERNS, SWAGGER_ENDPOINTS);

}
