package com.syemon.usersystem.dataaccess;

import com.syemon.usersystem.domain.UserQueryRepository;
import com.syemon.usersystem.service.RequestLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DataAccessConfiguration {

    @Bean
    public RequestLogRepository requestLogRepository(RequestLogJpaRepository requestLogJpaRepository) {
        return new PostgresLogRepository(requestLogJpaRepository);
    }

    @Bean
    public GithubClient githubClient(RestTemplate restTemplate, @Value("${github.api.base-url}") String baseUrl) {
        return new GithubClient(restTemplate, baseUrl);
    }

    @Bean
    public DataAccessUserMapper dataAccessUserMapper() {
        return new DataAccessUserMapper();
    }

    @Bean
    public UserQueryRepository userQueryRepository(GithubClient githubClient, DataAccessUserMapper dataAccessUserMapper) {
        return new GithubUserQueryRepository(githubClient, dataAccessUserMapper);
    }
}
