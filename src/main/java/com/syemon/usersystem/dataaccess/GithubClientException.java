package com.syemon.usersystem.dataaccess;

class GithubClientException extends RuntimeException {
    public GithubClientException(String message) {
        super(message);
    }

    public GithubClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
