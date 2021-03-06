package com.lsolier.user.api.usermanager.model.dto;

import com.lsolier.user.api.usermanager.annotation.PasswordValidation;
import com.lsolier.user.api.usermanager.utils.UserUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {

    @NotBlank(message = "User name cannot be empty")
    private String name;

    @Email(regexp = UserUtils.EMAIL_REGEX, message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @PasswordValidation
    private String password;

    @NotEmpty(message = "Phone list cannot be empty")
    @Valid
    private List<PhoneRequest> phones;

}
