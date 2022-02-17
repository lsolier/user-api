package com.lsolier.user.api.utils;

import java.util.UUID;

public final class UserUtil {

    private UserUtil() {
        throw new UnsupportedOperationException();
    }

    public static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[cl/CL]{2,})$";

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

}
