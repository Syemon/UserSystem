package com.syemon.usersystem.service;

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
}
