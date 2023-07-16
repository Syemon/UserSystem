package com.syemon.usersystem.service;

import com.syemon.usersystem.domain.User;
import com.syemon.usersystem.domain.UserLogin;
import com.syemon.usersystem.domain.UserNotFoundException;
import com.syemon.usersystem.domain.UserQueryRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class UserApplicationService {

    private final RequestValidator requestValidator;
    private final UserQueryRepository userQueryRepository;

    public UserResponse getUser(@Valid UserQuery userQuery) {
        requestValidator.validate(userQuery);

        UserLogin userLogin = new UserLogin(userQuery.login());
        Optional<User> optionalUser = userQueryRepository.getUser(userLogin);
        if (optionalUser.isEmpty()) {
            log.warn("User with login: '{}' does not exist", userLogin.value());
            throw new UserNotFoundException("Could not find user: " + userLogin.value());
        }
        return null;
    }

}
