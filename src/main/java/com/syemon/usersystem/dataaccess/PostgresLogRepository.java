package com.syemon.usersystem.dataaccess;

import com.syemon.usersystem.domain.UserLogin;
import com.syemon.usersystem.service.RequestLog;
import com.syemon.usersystem.service.RequestLogRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
class PostgresLogRepository implements RequestLogRepository {

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
