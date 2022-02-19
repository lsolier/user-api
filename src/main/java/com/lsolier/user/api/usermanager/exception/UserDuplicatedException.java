package com.lsolier.user.api.usermanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User already exist")
public class UserDuplicatedException extends RuntimeException {

    public UserDuplicatedException() {
    }

    public UserDuplicatedException(String message) {
        super(message);
    }

}
