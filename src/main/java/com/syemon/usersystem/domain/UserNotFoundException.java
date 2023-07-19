package com.syemon.usersystem.domain;

public class UserNotFoundException extends UserDomainException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
