package com.lsolier.user.api.exception;

public class UserDuplicatedException extends RuntimeException {

    public UserDuplicatedException() {
        super("User already exist");
    }

    public UserDuplicatedException(String message) {
        super(message);
    }

}
