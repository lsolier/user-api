package com.lsolier.user.api.usermanager.resource;

import com.lsolier.user.api.usermanager.model.dto.CreateUserRequest;
import com.lsolier.user.api.usermanager.model.dto.UpdateUserRequest;
import com.lsolier.user.api.usermanager.model.dto.UserDetailResponse;
import com.lsolier.user.api.usermanager.model.dto.UserResponse;
import com.lsolier.user.api.usermanager.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/users")
@ApiResponses(value = {
        @ApiResponse(code=400, message = "This is a bad request, please follow the API documentation for the proper request format."),
        @ApiResponse(code=401, message = "Due to security constraints, your access request cannot be authorized. "),
        @ApiResponse(code=500, message = "The server is down. Please make sure that the Location microservice is running.")
})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    List<UserResponse> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("/{id}")
    UserDetailResponse getUser(@PathVariable("id") String id) {
        return this.userService.getUser(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserResponse createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return this.userService.saveUser(createUserRequest);
    }

    @PatchMapping("/{id}")
    UserResponse updateUser(@PathVariable("id") String id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return this.userService.updateUser(id, updateUserRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable("id") String id) {
        this.userService.deleteUser(id);
    }

}
