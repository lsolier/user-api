package com.lsolier.user.api.utils;

import com.lsolier.user.api.model.dto.PhoneRequest;
import com.lsolier.user.api.model.dto.UserRequest;
import com.lsolier.user.api.model.dto.UserResponse;
import com.lsolier.user.api.model.entity.PhoneEntity;
import com.lsolier.user.api.model.entity.UserEntity;

import java.util.function.Function;
import java.util.stream.Collectors;

public final class UserMapper {

    private UserMapper() {
        throw new UnsupportedOperationException();
    }

    public static UserEntity mapToUserEntity(String userId, String token, UserRequest userRequest) {
        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .token(token)
                .isActive(Boolean.TRUE)
                .build();

        Function<PhoneRequest, PhoneEntity> toEntity = phoneRequest -> mapToPhoneEntity(userEntity, phoneRequest);
        userEntity.setPhones(userRequest.getPhones().stream().sequential().map(toEntity).collect(Collectors.toList()));

        return userEntity;
    }

    private static PhoneEntity mapToPhoneEntity(UserEntity userEntity, PhoneRequest phoneRequest) {
        return PhoneEntity.builder()
                .user(userEntity)
                .number(phoneRequest.getNumber())
                .cityCode(phoneRequest.getCityCode())
                .countryCode(phoneRequest.getCountryCode())
                .build();
    }

    public static UserResponse mapToUserResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .created(userEntity.getCreated())
                .modified(userEntity.getModified())
                .lastLogin(userEntity.getLastLogin())
                .token(userEntity.getToken())
                .isActive(userEntity.isActive())
                .build();
    }
}
