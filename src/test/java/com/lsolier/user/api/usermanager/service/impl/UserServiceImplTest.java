package com.lsolier.user.api.usermanager.service.impl;

import com.lsolier.user.api.session.UserApiStore;
import com.lsolier.user.api.exception.UserDuplicatedException;
import com.lsolier.user.api.exception.UserNotFoundException;
import com.lsolier.user.api.usermanager.common.UserTestData;
import com.lsolier.user.api.usermanager.model.dto.UserDetailResponse;
import com.lsolier.user.api.usermanager.model.dto.UserResponse;
import com.lsolier.user.api.usermanager.model.entity.UserEntity;
import com.lsolier.user.api.usermanager.repository.UserRepository;
import com.lsolier.user.api.usermanager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserApiStore userApiStore;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.userService = new UserServiceImpl(this.userRepository, this.userApiStore);
    }

    @Test
    public void shouldReturnAllUsers() {
        given(this.userRepository.findAll()).willReturn(Collections.singletonList(UserTestData.buildUserEntityMock()));
        List<UserResponse> response = this.userService.getAllUsers();
        assertThat(response).isNotEmpty();
        assertThat(response.get(0).getId()).isEqualTo("id=2e6ef266-f642-45af-b39f-0216cbab52ca");
    }

    @Test
    public void givenId_serviceShouldReturnUser() {
        given(this.userRepository.findById(anyString())).willReturn(Optional.of(UserTestData.buildUserEntityMock()));
        UserDetailResponse response = this.userService.getUser("id=2e6ef266-f642-45af-b39f-0216cbab52ca");
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Luis Solier Sajami");
    }

    @Test
    public void givenCreateUserRequest_serviceShouldRegisterUser() {
        given(this.userRepository.findByEmail(anyString())).willReturn(Optional.empty());
        given(this.userRepository.save(any(UserEntity.class))).willReturn(UserTestData.buildUserEntityMock());
        given(this.userApiStore.getToken()).willReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXBlcnVzZXIiLCJleHAiOjE2NDUzMjA4NjR9.hA7i9O7zQAo0gkHvPMe2L5ViH3CQI2a-L_XPVpEJhaQ");
        UserResponse response = this.userService.saveUser(UserTestData.buildCreateUserRequestMock());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo("id=2e6ef266-f642-45af-b39f-0216cbab52ca");
    }

    @Test
    public void givenCreateUserRequest_withExistingEmail_serviceShouldReturnUserDuplicatedException() {
        given(this.userRepository.findByEmail(anyString())).willReturn(Optional.of(UserTestData.buildUserEntityMock()));
        assertThatExceptionOfType(UserDuplicatedException.class)
                .isThrownBy(() -> {
                    this.userService.saveUser(UserTestData.buildCreateUserRequestMock());
                });
    }

    @Test
    public void givenIdAndUpdateUserRequest_serviceShouldUpdateUser() {
        given(this.userRepository.save(any(UserEntity.class))).willReturn(UserTestData.buildUpdatedUserEntityMock());
        given(this.userRepository.findById(anyString())).willReturn(Optional.of(UserTestData.buildUserEntityMock()));
        UserResponse response = this.userService.updateUser("IdTest", UserTestData.buildUpdateUserRequestMock());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo("id=2e6ef266-f642-45af-b39f-0216cbab52ca");
    }

    @Test
    public void givenIdAndUpdateUserRequest_whenUserDoesNotExist_serviceShouldReturnUserNotFoundException() {
        given(this.userRepository.findById(anyString())).willReturn(Optional.empty());
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> {
                    this.userService.updateUser("IdTest", UserTestData.buildUpdateUserRequestMock());
                });
    }

    @Test
    public void givenId_serviceShouldDeleteUser() {
        given(this.userRepository.findById(anyString())).willReturn(Optional.of(UserTestData.buildUserEntityMock()));
        doNothing().when(this.userRepository).delete(any(UserEntity.class));
        this.userService.deleteUser("IdTest");
        verify(this.userRepository, times(1)).delete(any(UserEntity.class));
    }

}