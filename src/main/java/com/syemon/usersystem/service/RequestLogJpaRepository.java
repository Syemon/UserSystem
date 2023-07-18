package com.syemon.usersystem.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestLogJpaRepository extends JpaRepository<RequestLog, Long> {

    Optional<RequestLog> findByLogin(String login);
}
