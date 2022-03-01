package com.lsolier.user.api.usermanager.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<PasswordValidation, String> {

    private final String passwordValidationRegex;

    public PasswordValidator(@Value("${password.validation.regexp}") String passwordValidationRegex) {
        this.passwordValidationRegex = passwordValidationRegex;
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(this.passwordValidationRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
