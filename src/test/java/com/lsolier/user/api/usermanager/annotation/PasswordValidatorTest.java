package com.lsolier.user.api.usermanager.annotation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordValidatorTest {

    private final static String REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    @ParameterizedTest
    @ValueSource(strings = {"Superpassword2022$", "asdsadas@Td2ds2$", "sghsudsfsd#5145WE", "ierysdvjbcds$9854WTW"})
    public void givenSetOfPassword_shouldReturnTrue(String password) {
        PasswordValidator passwordValidator = new PasswordValidator(REGEX);
        assertThat(passwordValidator.isValid(password, null)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"uperpassword2022$", "asdsadasTd2ds2", "sghsudsfsd#WE", "ie$Wy2"})
    public void givenSetOfInvalidPassword_shouldReturnFalse(String password) {
        PasswordValidator passwordValidator = new PasswordValidator(REGEX);
        assertThat(passwordValidator.isValid(password, null)).isFalse();
    }

}