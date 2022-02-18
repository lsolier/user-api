package com.lsolier.user.api.service.impl;

import com.lsolier.user.api.exception.UserNotFoundException;
import com.lsolier.user.api.model.dto.CreateUserRequest;
import com.lsolier.user.api.model.dto.UpdateUserRequest;
import com.lsolier.user.api.model.dto.UserDetailResponse;
import com.lsolier.user.api.model.dto.UserResponse;
import com.lsolier.user.api.model.entity.UserEntity;
import com.lsolier.user.api.repository.UserRepository;
import com.lsolier.user.api.service.UserService;
import com.lsolier.user.api.utils.UserMapper;
import com.lsolier.user.api.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
        //TODO validar email
        String userId = UserUtil.generateId();
        //TODO obtener token
        UserEntity userEntity = UserMapper.mapToUserEntity(userId, "token", createUserRequest);

        log.info("User to register: {}", userEntity);

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

}
