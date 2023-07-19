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
    private final RequestLogRepository requestLogRepository;
    private final UserMapper userMapper;

    public UserResponse getUser(@Valid UserQuery userQuery) {
        requestValidator.validate(userQuery);

        UserLogin userLogin = new UserLogin(userQuery.login());

        logRequest(userLogin);

        Optional<User> optionalUser = userQueryRepository.getUser(userLogin);
        if (optionalUser.isEmpty()) {
            log.warn("User with login: '{}' does not exist", userLogin.value());
            throw new UserNotFoundException("Could not find user: " + userLogin.value());
        }
        User user = optionalUser.get();
        user.updateWithCalculation();
        return userMapper.userToUserResponse(user);
    }

    private void logRequest(UserLogin userLogin) {
        try {
            RequestLog requestLog = getOrCreateRequestLog(userLogin);
            incrementAndSaveRequestLog(requestLog);
        } catch (Exception e) {
            log.error("An error has occurred while saving request log", e);
            throw e;
        }
    }

    private RequestLog getOrCreateRequestLog(UserLogin userLogin) {
        Optional<RequestLog> requestLog = requestLogRepository.findByLogin(userLogin);

        return requestLog.orElseGet(() -> RequestLog.builder()
                .login(userLogin.value())
                .build());
    }

    private void incrementAndSaveRequestLog(RequestLog requestLog) {
        requestLog.incrementRequestCount();
        requestLogRepository.save(requestLog);
    }

}
