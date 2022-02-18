package com.lsolier.user.api.usermanager.service;

import com.lsolier.user.api.usermanager.model.dto.CreateUserRequest;
import com.lsolier.user.api.usermanager.model.dto.UpdateUserRequest;
import com.lsolier.user.api.usermanager.model.dto.UserDetailResponse;
import com.lsolier.user.api.usermanager.model.dto.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserDetailResponse getUser(String id);

    UserResponse saveUser(CreateUserRequest createUserRequest);

    UserResponse updateUser(String id, UpdateUserRequest updateUserRequest);

    void deleteUser(String id);
}
