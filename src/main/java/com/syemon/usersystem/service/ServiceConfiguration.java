package com.syemon.usersystem.service;

import com.syemon.usersystem.domain.UserQueryRepository;
import jakarta.validation.Validator;
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
    public UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    public RequestValidator requestValidator(Validator validator) {
        return new RequestValidator(validator);
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

}
