package com.lsolier.user.api.token.utils;

public final class TokenUtil {

    private TokenUtil() {
        throw new UnsupportedOperationException();
    }

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String BEARER_PREFIX = "Bearer ";

    public static final String TOKEN_ENDPOINT = "/tokens";

    public static final String H2_ENDPOINTS = "/h2-console/**";

    public static final String ACTUATOR_ENDPOINTS = "/actuator/**";

    public static final String[] PERMIT_ALL_PATTERNS = new String[] { TOKEN_ENDPOINT, H2_ENDPOINTS, ACTUATOR_ENDPOINTS };

}
