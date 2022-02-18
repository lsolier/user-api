package com.lsolier.user.api.usermanager.utils;

import com.lsolier.user.api.usermanager.model.dto.PhoneRequest;
import com.lsolier.user.api.usermanager.model.dto.CreateUserRequest;
import com.lsolier.user.api.usermanager.model.dto.PhoneResponse;
import com.lsolier.user.api.usermanager.model.dto.UpdateUserRequest;
import com.lsolier.user.api.usermanager.model.dto.UserDetailResponse;
import com.lsolier.user.api.usermanager.model.dto.UserResponse;
import com.lsolier.user.api.usermanager.model.entity.PhoneEntity;
import com.lsolier.user.api.usermanager.model.entity.UserEntity;

import java.util.function.Function;
import java.util.stream.Collectors;

public final class UserMapper {

    private UserMapper() {
        throw new UnsupportedOperationException();
    }

    public static UserEntity mapToUserEntity(UserEntity userEntity, UpdateUserRequest updateUserRequest) {
        userEntity.setName(updateUserRequest.getName());
        userEntity.setEmail(updateUserRequest.getEmail());
        userEntity.setPassword(updateUserRequest.getEmail());

        Function<PhoneRequest, PhoneEntity> toEntity = phoneRequest -> mapToPhoneEntity(userEntity, phoneRequest);
        userEntity.setPhones(updateUserRequest.getPhones().stream().sequential().map(toEntity).collect(Collectors.toList()));

        return userEntity;
    }

    public static UserEntity mapToUserEntity(String userId, String token, CreateUserRequest createUserRequest) {
        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .name(createUserRequest.getName())
                .email(createUserRequest.getEmail())
                .password(createUserRequest.getPassword())
                .token(token)
                .isActive(Boolean.TRUE)
                .build();

        Function<PhoneRequest, PhoneEntity> toEntity = phoneRequest -> mapToPhoneEntity(userEntity, phoneRequest);
        userEntity.setPhones(createUserRequest.getPhones().stream().sequential().map(toEntity).collect(Collectors.toList()));

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

    public static UserDetailResponse mapToUserDetailResponse(UserEntity userEntity) {
        return UserDetailResponse.builder()
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .phones(userEntity.getPhones().stream().map(UserMapper::mapToPhoneResponse).collect(Collectors.toList()))
                .isActive(userEntity.isActive())
                .build();
    }

    public static PhoneResponse mapToPhoneResponse(PhoneEntity phoneEntity) {
        return PhoneResponse.builder()
                .number(phoneEntity.getNumber())
                .cityCode(phoneEntity.getCityCode())
                .countryCode(phoneEntity.getCountryCode())
                .build();
    }
}
