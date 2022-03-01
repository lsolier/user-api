package com.lsolier.user.api.usermanager.common;

import com.lsolier.user.api.usermanager.model.dto.CreateUserRequest;
import com.lsolier.user.api.usermanager.model.dto.PhoneRequest;
import com.lsolier.user.api.usermanager.model.dto.UpdateUserRequest;
import com.lsolier.user.api.usermanager.model.entity.PhoneEntity;
import com.lsolier.user.api.usermanager.model.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class UserTestData {

    private UserTestData() {
        throw new UnsupportedOperationException();
    }

    public static CreateUserRequest buildCreateUserRequestMock() {
        return CreateUserRequest.builder()
                .name("Luis Solier Sajami")
                .email("luis.sajami@company.cl")
                .password("Superpassword2022$")
                .phones(Collections.singletonList(buildPhoneRequestMock()))
                .build();

    }

    public static UpdateUserRequest buildUpdateUserRequestMock() {
        return UpdateUserRequest.builder()
                .name("Luis Solier Sajami - Updated")
                .email("luis.sajami.updated@company.cl")
                .phones(Collections.singletonList(buildPhoneRequestMock()))
                .build();

    }

    private static PhoneRequest buildPhoneRequestMock() {
        return PhoneRequest.builder()
                .number("993302384")
                .cityCode("01")
                .countryCode("+51")
                .build();
    }

    public static UserEntity buildUserEntityMock() {
        UserEntity userEntity = UserEntity.builder()
                .id("id=2e6ef266-f642-45af-b39f-0216cbab52ca")
                .name("Luis Solier Sajami")
                .email("luis.sajami@company.cl")
                .password("{bcrypt}$2a$10$6unqaSY0/amYy.YgifHNeuJf9FnUiPqBislu0.ExANO/9ThmpgP0q")
                .lastLogin(LocalDateTime.now())
                .token("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXBlcnVzZXIiLCJleHAiOjE2NDUzMjA4NjR9.hA7i9O7zQAo0gkHvPMe2L5ViH3CQI2a-L_XPVpEJhaQ")
                .isActive(Boolean.TRUE)
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .build();

        List<PhoneEntity> phoneEntityList = new ArrayList<>();
        phoneEntityList.add(buildPhoneEntityMock());
        userEntity.setPhones(phoneEntityList);
        return userEntity;

    }

    public static UserEntity buildUpdatedUserEntityMock() {
        UserEntity userEntity = UserEntity.builder()
                .id("id=2e6ef266-f642-45af-b39f-0216cbab52ca")
                .name("Luis Solier Sajami - Updated")
                .email("luis.sajami.updated@company.cl")
                .password("{bcrypt}$2a$10$6unqaSY0/amYy.YgifHNeuJf9FnUiPqBislu0.ExANO/9ThmpgP0q")
                .lastLogin(LocalDateTime.now())
                .token("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXBlcnVzZXIiLCJleHAiOjE2NDUzMjA4NjR9.hA7i9O7zQAo0gkHvPMe2L5ViH3CQI2a-L_XPVpEJhaQ")
                .isActive(Boolean.TRUE)
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .build();

        List<PhoneEntity> phoneEntityList = new ArrayList<>();
        phoneEntityList.add(buildPhoneEntityMock());
        userEntity.setPhones(phoneEntityList);
        return userEntity;

    }

    private static PhoneEntity buildPhoneEntityMock() {
        return PhoneEntity.builder()
                .id(1L)
                .number("993302384")
                .cityCode("01")
                .countryCode("+51")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();
    }
}
