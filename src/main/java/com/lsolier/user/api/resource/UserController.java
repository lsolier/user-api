package com.lsolier.user.api.resource;

import com.lsolier.user.api.model.dto.UserRequest;
import com.lsolier.user.api.model.dto.UserResponse;
import com.lsolier.user.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController()
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserResponse post(@Valid @RequestBody UserRequest userRequest) {
        return this.userService.saveUser(userRequest);
    }
}
