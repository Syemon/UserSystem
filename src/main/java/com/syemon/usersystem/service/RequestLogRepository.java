package com.syemon.usersystem.service;

import com.syemon.usersystem.domain.UserLogin;

import java.util.Optional;

public interface RequestLogRepository {

    Optional<RequestLog> findByLogin(UserLogin login);

    RequestLog save(RequestLog requestLog);
}
