package com.lsolier.user.api.usermanager.service.impl;

import com.lsolier.user.api.session.UserApiStore;
import com.lsolier.user.api.exception.UserDuplicatedException;
import com.lsolier.user.api.exception.UserNotFoundException;
import com.lsolier.user.api.usermanager.model.dto.CreateUserRequest;
import com.lsolier.user.api.usermanager.model.dto.PhoneRequest;
import com.lsolier.user.api.usermanager.model.dto.UpdateUserRequest;
import com.lsolier.user.api.usermanager.model.dto.UserDetailResponse;
import com.lsolier.user.api.usermanager.model.dto.UserResponse;
import com.lsolier.user.api.usermanager.model.entity.PhoneEntity;
import com.lsolier.user.api.usermanager.model.entity.UserEntity;
import com.lsolier.user.api.usermanager.repository.UserRepository;
import com.lsolier.user.api.usermanager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        given(this.userRepository.findAll()).willReturn(Collections.singletonList(buildUserEntityMock()));
        List<UserResponse> response = this.userService.getAllUsers();
        assertThat(response).isNotEmpty();
        assertThat(response.get(0).getId()).isEqualTo("id=2e6ef266-f642-45af-b39f-0216cbab52ca");
    }

    @Test
    public void givenId_serviceShouldReturnUser() {
        given(this.userRepository.findById(anyString())).willReturn(Optional.of(buildUserEntityMock()));
        UserDetailResponse response = this.userService.getUser("id=2e6ef266-f642-45af-b39f-0216cbab52ca");
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Luis Solier Sajami");
    }

    @Test
    public void givenCreateUserRequest_serviceShouldRegisterUser() {
        given(this.userRepository.findByEmail(anyString())).willReturn(Optional.empty());
        given(this.userRepository.save(any(UserEntity.class))).willReturn(buildUserEntityMock());
        given(this.userApiStore.getToken()).willReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXBlcnVzZXIiLCJleHAiOjE2NDUzMjA4NjR9.hA7i9O7zQAo0gkHvPMe2L5ViH3CQI2a-L_XPVpEJhaQ");
        UserResponse response = this.userService.saveUser(buildCreateUserRequestMock());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo("id=2e6ef266-f642-45af-b39f-0216cbab52ca");
    }

    @Test
    public void givenCreateUserRequest_withExistingEmail_serviceShouldReturnUserDuplicatedException() {
        given(this.userRepository.findByEmail(anyString())).willReturn(Optional.of(buildUserEntityMock()));
        assertThatExceptionOfType(UserDuplicatedException.class)
                .isThrownBy(() -> {
                    this.userService.saveUser(buildCreateUserRequestMock());
                });
    }

    @Test
    public void givenIdAndUpdateUserRequest_serviceShouldUpdateUser() {
        given(this.userRepository.save(any(UserEntity.class))).willReturn(buildUpdatedUserEntityMock());
        given(this.userRepository.findById(anyString())).willReturn(Optional.of(buildUserEntityMock()));
        UserResponse response = this.userService.updateUser("IdTest", buildUpdateUserRequestMock());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo("id=2e6ef266-f642-45af-b39f-0216cbab52ca");
    }

    @Test
    public void givenIdAndUpdateUserRequest_whenUserDoesNotExist_serviceShouldReturnUserNotFoundException() {
        given(this.userRepository.findById(anyString())).willReturn(Optional.empty());
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> {
                    this.userService.updateUser("IdTest", buildUpdateUserRequestMock());
                });
    }

    @Test
    public void givenId_serviceShouldDeleteUser() {
        given(this.userRepository.findById(anyString())).willReturn(Optional.of(buildUserEntityMock()));
        doNothing().when(this.userRepository).delete(any(UserEntity.class));
        this.userService.deleteUser("IdTest");
        verify(this.userRepository, times(1)).delete(any(UserEntity.class));
    }

    private CreateUserRequest buildCreateUserRequestMock() {
        return CreateUserRequest.builder()
                .name("Luis Solier Sajami")
                .email("luis.sajami@company.cl")
                .password("Superpassword2022$")
                .phones(Collections.singletonList(buildPhoneRequestMock()))
                .build();

    }

    private UpdateUserRequest buildUpdateUserRequestMock() {
        return UpdateUserRequest.builder()
                .name("Luis Solier Sajami - Updated")
                .email("luis.sajami.updated@company.cl")
                .phones(Collections.singletonList(buildPhoneRequestMock()))
                .build();

    }

    private PhoneRequest buildPhoneRequestMock() {
        return PhoneRequest.builder()
                .number("993302384")
                .cityCode("01")
                .countryCode("+51")
                .build();
    }

    private UserEntity buildUserEntityMock() {
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

    private UserEntity buildUpdatedUserEntityMock() {
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

    private PhoneEntity buildPhoneEntityMock() {
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