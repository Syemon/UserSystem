package com.syemon.usersystem.service;

import com.syemon.usersystem.domain.User;
import com.syemon.usersystem.domain.UserDomainException;
import com.syemon.usersystem.domain.UserQueryRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class GithubUserQueryRepository implements UserQueryRepository {

    private final GithubClient githubClient;
    private final UserMapper userMapper;
    @Override
    public Optional<User> getUser(String userName) {
        Optional<GithubUser> githubUser = tryGetUser(userName);
        return githubUser.map(userMapper::githubUserToUser);
    }

    private Optional<GithubUser> tryGetUser(String userName) {
        try {
            return githubClient.getUser(userName);
        } catch (GithubClientException e) {
            throw new UserDomainException("Received Client exception", e);
        } catch (Exception e) {
            throw new UserDomainException("Unexpected exception was thrown", e);
        }
    }
}
