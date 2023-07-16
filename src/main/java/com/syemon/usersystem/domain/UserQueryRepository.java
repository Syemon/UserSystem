package com.syemon.usersystem.domain;

public interface UserQueryRepository {

    User getUser(String userName);
}
