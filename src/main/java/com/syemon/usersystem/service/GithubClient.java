package com.syemon.usersystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class GithubClient {

    private final static String USERS_PATH = "/users/";
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public GithubClient(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public GithubUser getUser(String userName) {
        String url = baseUrl + USERS_PATH + userName;

        return restTemplate.getForEntity(url, GithubUser.class).getBody();
    }
}
