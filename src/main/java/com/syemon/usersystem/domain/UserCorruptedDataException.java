package com.syemon.usersystem.domain;

public class UserCorruptedDataException extends UserDomainException {
    public UserCorruptedDataException(String message) {
        super(message);
    }

    public UserCorruptedDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
