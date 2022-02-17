package com.lsolier.user.api.service.impl;

import com.lsolier.user.api.model.dto.UserRequest;
import com.lsolier.user.api.model.dto.UserResponse;
import com.lsolier.user.api.model.entity.UserEntity;
import com.lsolier.user.api.repository.UserRepository;
import com.lsolier.user.api.service.UserService;
import com.lsolier.user.api.utils.UserMapper;
import com.lsolier.user.api.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        String userId = UserUtil.generateId();
        UserEntity userEntity = UserMapper.mapToUserEntity(userId, "token", userRequest);

        log.info("User to register: {}", userEntity);

        return UserMapper.mapToUserResponse(this.userRepository.save(userEntity));
    }
}
