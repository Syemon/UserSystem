package com.syemon.usersystem.service;

import com.syemon.usersystem.domain.UserLogin;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class PostgresLogRepository implements RequestLogRepository {

    private final RequestLogJpaRepository requestLogJpaRepository;

    @Override
    public Optional<RequestLog> findByLogin(UserLogin login) {
        return requestLogJpaRepository.findByLogin(login.value());
    }

    @Override
    public RequestLog save(RequestLog requestLog) {
        return requestLogJpaRepository.save(requestLog);
    }
}
