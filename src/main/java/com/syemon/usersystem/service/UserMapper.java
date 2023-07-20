package com.syemon.usersystem.service;

import com.syemon.usersystem.domain.User;

import java.math.BigDecimal;

class UserMapper {

    public UserResponse userToUserResponse(User user) {
        return new UserResponse(
                user.getId().value().toString(),
                user.getLogin().value(),
                user.getName(),
                user.getType(),
                user.getAvatarUrl(),
                user.getCreatedAt().toString(),
                user.getCalculations()
                        .map(BigDecimal::toString)
                        .orElse(null)
        );
    }
}
