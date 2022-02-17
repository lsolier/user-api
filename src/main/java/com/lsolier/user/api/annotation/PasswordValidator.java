package com.lsolier.user.api.annotation;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<PasswordValidation, String> {

    @Value("${password.validation.regexp}")
    private String passwordValidationRegex;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(this.passwordValidationRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
