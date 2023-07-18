package com.syemon.usersystem.service;

import com.syemon.usersystem.domain.User;
import com.syemon.usersystem.domain.UserId;
import com.syemon.usersystem.domain.UserLogin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class UserMapper {

    private final static DateTimeFormatter GITHUB_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    User githubUserToUser(GithubUser githubUser) {
        return User.builder()
                .id(new UserId(githubUser.id()))
                .login(new UserLogin(githubUser.login()))
                .name(githubUser.name())
                .type(githubUser.type())
                .avatarUrl(githubUser.avatar_url())
                .followersCount(githubUser.followers())
                .repositoriesCount(githubUser.public_repos())
                .createdAt(LocalDateTime.parse(githubUser.created_at(), GITHUB_DATE_TIME_FORMATTER))
                .build();
    }

    public UserResponse userToUserResponse(User user) {
        return new UserResponse(
                user.getId().value().toString(),
                user.getLogin().value(),
                user.getName(),
                user.getType(),
                user.getAvatarUrl(),
                user.getCreatedAt().toString(),
                user.getCalculations() != null
                        ? user.getCalculations().toString()
                        : null
        );
    }
}
