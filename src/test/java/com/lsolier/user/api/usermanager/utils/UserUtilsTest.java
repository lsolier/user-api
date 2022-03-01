package com.lsolier.user.api.usermanager.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserUtilsTest {

    @Test
    public void shouldGenerateId() {
        String uuid = UserUtils.generateId();
        assertThat(uuid).isNotBlank();
    }
}
