package com.syemon.usersystem.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserQuery(@NotBlank @NotEmpty @Size(min = 1, max = 39) String login) {
}
