package com.lsolier.user.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneRequest {

    @NotBlank(message = "Phone number cannot be empty")
    private String number;

    @NotBlank(message = "City code cannot be empty")
    private String cityCode;

    @NotBlank(message = "Country code cannot be empty")
    private String countryCode;
}