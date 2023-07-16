package com.syemon.usersystem.service;

import com.syemon.usersystem.domain.User;
import com.syemon.usersystem.domain.UserDomainException;
import com.syemon.usersystem.domain.UserLogin;
import com.syemon.usersystem.domain.UserQueryRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
class GithubUserQueryRepository implements UserQueryRepository {

    private final GithubClient githubClient;
    private final UserMapper userMapper;
    @Override
    public Optional<User> getUser(UserLogin login) {
        Optional<GithubUser> githubUser = tryGetUser(login);
        return githubUser.map(userMapper::githubUserToUser);
    }

    private Optional<GithubUser> tryGetUser(UserLogin userName) {
        try {
            return githubClient.getUser(userName);
        } catch (GithubClientException e) {
            throw new UserDomainException("Received Client exception", e);
        } catch (Exception e) {
            throw new UserDomainException("Unexpected exception was thrown", e);
        }
    }
}
