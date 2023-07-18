package com.syemon.usersystem.service;

import com.syemon.usersystem.domain.UserQueryRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public GithubClient githubClient(RestTemplate restTemplate, @Value("${github.api.base-url}") String baseUrl) {
        return new GithubClient(restTemplate, baseUrl);
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    public RequestValidator requestValidator(Validator validator) {
        return new RequestValidator(validator);
    }

    @Bean
    public UserQueryRepository userQueryRepository(GithubClient githubClient, UserMapper userMapper) {
        return new GithubUserQueryRepository(githubClient, userMapper);
    }
    @Bean
    public UserApplicationService userApplicationService(
            RequestValidator requestValidator,
            UserQueryRepository userQueryRepository,
            RequestLogRepository requestLogRepository,
            UserMapper userMapper
    ) {
        return new UserApplicationService(requestValidator, userQueryRepository, requestLogRepository, userMapper);
    }

    @Bean
    public RequestLogRepository requestLogRepository(RequestLogJpaRepository requestLogJpaRepository) {
        return new PostgresLogRepository(requestLogJpaRepository);
    }
}
