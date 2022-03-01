package com.lsolier.user.api.usermanager.utils;

import com.lsolier.user.api.usermanager.common.UserTestData;
import com.lsolier.user.api.usermanager.model.dto.UserDetailResponse;
import com.lsolier.user.api.usermanager.model.dto.UserResponse;
import com.lsolier.user.api.usermanager.model.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

    @Test
    public void givenUserEntityAndUpdateUserRequest_shouldReturnUserEntity_withUpdatedData() {
        UserEntity userEntity = UserMapper.mapToUserEntity(UserTestData.buildUserEntityMock(), UserTestData.buildUpdateUserRequestMock());
        assertThat(userEntity).isNotNull();
    }

    @Test
    public void givenIdAndTokenAndCreateUserRequestAndEncoder_shouldReturnUserEntity() {
        String testId = "TestId";
        UserEntity userEntity = UserMapper.mapToUserEntity(testId, "TestToken", UserTestData.buildCreateUserRequestMock(), PasswordEncoderFactories.createDelegatingPasswordEncoder());
        assertThat(userEntity).isNotNull();
        assertThat(userEntity.getId()).isEqualTo(testId);
    }

    @Test
    public void givenUserEntity_shouldReturnUserResponse() {
        UserResponse userResponse = UserMapper.mapToUserResponse(UserTestData.buildUserEntityMock());
        assertThat(userResponse).isNotNull();
    }

    @Test
    public void givenUserEntity_shouldReturnUserDetailResponse() {
        UserDetailResponse userDetailResponse = UserMapper.mapToUserDetailResponse(UserTestData.buildUserEntityMock());
        assertThat(userDetailResponse).isNotNull();
        assertThat(userDetailResponse.getPhones()).isNotEmpty();
    }
}
