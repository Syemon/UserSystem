package com.syemon.usersystem.dataaccess;

import com.syemon.usersystem.domain.UserLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
class GithubClient {

    private final static String USERS_PATH = "/users/";
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public GithubClient(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public Optional<GithubUser> getUser(UserLogin login) {
        String url = baseUrl + USERS_PATH + login.value();

        try {
            return Optional.ofNullable(restTemplate.getForEntity(url, GithubUser.class).getBody());
        } catch (HttpClientErrorException e) {
            if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                log.info("User {} does not exist in github", login);
                return Optional.empty();
            }
            log.error("Received HttpClientErrorException: {}", e.getMessage(), e);
            throw new GithubClientException("Client has thrown an exception", e);
        } catch (Exception e) {
            log.error("Received unexpected exception: {}", e.getMessage(), e);
            throw new GithubClientException("Unexpected exception", e);
        }
    }
}
