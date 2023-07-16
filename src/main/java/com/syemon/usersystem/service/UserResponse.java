package com.syemon.usersystem.service;

public record UserResponse (
        String id,
        String login,
        String name,
        String type,
        String avatarUrl,
        String createdAt,
        String calculations
) {
    
}
