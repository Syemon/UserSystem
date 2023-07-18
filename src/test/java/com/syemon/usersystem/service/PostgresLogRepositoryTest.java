package com.syemon.usersystem.service;

import com.syemon.usersystem.PostgresTestContainerResourceTest;
import com.syemon.usersystem.domain.UserLogin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostgresLogRepositoryTest extends PostgresTestContainerResourceTest {

    public static final String NOT_EXISTENT_LOGIN = "NON_EXISTENT";
    public static final String EXISTENT_LOGIN = UUID.randomUUID().toString();
    @Autowired
    private RequestLogRepository sut;

    @Test
    void findByLogin_whenDoesNotExist() {
        assertThat(
                sut.findByLogin(new UserLogin(NOT_EXISTENT_LOGIN))
        ).isNotPresent();
    }

    @Test
    void findByLogin_whenExists() {
        //given
        String existent = EXISTENT_LOGIN;
        RequestLog requestLog = RequestLog.builder()
                .login(existent)
                .build();

        sut.save(requestLog);

        //when
        Optional<RequestLog> result = sut.findByLogin(new UserLogin(existent));

        // then
        assertThat(result).isPresent();

        RequestLog persistedLog = result.get();

        assertThat(persistedLog.getLogin()).isEqualTo(EXISTENT_LOGIN);
        assertThat(persistedLog.getId()).isNotNull();
        assertThat(persistedLog.getRequestCount()).isZero();
        assertThat(persistedLog.getCreatedAt()).isNotNull();
        assertThat(persistedLog.getModifiedAt()).isNull();
    }
}