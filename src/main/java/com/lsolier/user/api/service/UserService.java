package com.lsolier.user.api.service;

import com.lsolier.user.api.model.dto.UserRequest;
import com.lsolier.user.api.model.dto.UserResponse;

public interface UserService {

    UserResponse saveUser(UserRequest userRequest);
}
