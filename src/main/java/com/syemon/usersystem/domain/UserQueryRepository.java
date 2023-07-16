package com.syemon.usersystem.domain;

import java.util.Optional;

public interface UserQueryRepository {

    Optional<User> getUser(String userName);
}
