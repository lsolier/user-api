package com.lsolier.user.api.usermanager.service.impl;

import com.lsolier.user.api.session.UserApiStore;
import com.lsolier.user.api.exception.UserDuplicatedException;
import com.lsolier.user.api.exception.UserNotFoundException;
import com.lsolier.user.api.usermanager.model.dto.CreateUserRequest;
import com.lsolier.user.api.usermanager.model.dto.UpdateUserRequest;
import com.lsolier.user.api.usermanager.model.dto.UserDetailResponse;
import com.lsolier.user.api.usermanager.model.dto.UserResponse;
import com.lsolier.user.api.usermanager.model.entity.UserEntity;
import com.lsolier.user.api.usermanager.repository.UserRepository;
import com.lsolier.user.api.usermanager.service.UserService;
import com.lsolier.user.api.usermanager.utils.UserMapper;
import com.lsolier.user.api.usermanager.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserApiStore userApiStore;

    @Override
    public List<UserResponse> getAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(UserMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailResponse getUser(String id) {
        return UserMapper.mapToUserDetailResponse(this.userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public UserResponse saveUser(CreateUserRequest createUserRequest) {
        validateEmail(createUserRequest.getEmail());
        String userId = UserUtils.generateId();
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserEntity userEntity = UserMapper.mapToUserEntity(userId, this.userApiStore.getToken(), createUserRequest, encoder);
        return UserMapper.mapToUserResponse(this.userRepository.save(userEntity));
    }

    @Override
    public UserResponse updateUser(String id, UpdateUserRequest updateUserRequest) {
        Function<UserEntity, UserEntity> toUserEntityUpdated = userEntity -> UserMapper.mapToUserEntity(userEntity, updateUserRequest);
        UserEntity userEntityToBeUpdated = this.userRepository.findById(id)
                .map(toUserEntityUpdated)
                .orElseThrow(UserNotFoundException::new);
        return UserMapper.mapToUserResponse(this.userRepository.save(userEntityToBeUpdated));
    }

    @Override
    public void deleteUser(String id) {
        UserEntity userEntityToBeDeleted= this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        this.userRepository.delete(userEntityToBeDeleted);
    }

    private void validateEmail(String email) {
        this.userRepository.findByEmail(email).ifPresent(userEntity -> {
            log.error("User email already exist, User found: {}", userEntity);
            throw new UserDuplicatedException();
        });
    }

}
