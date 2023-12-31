package com.syemon.usersystem.service;

import com.syemon.usersystem.domain.User;
import com.syemon.usersystem.domain.UserId;
import com.syemon.usersystem.domain.UserLogin;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private UserMapper sut = new UserMapper();

    @Test
    void userToUserResponse() {
        //given
        LocalDateTime createdAt = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
        User user = User.builder()
                .id(new UserId(12L))
                .login(new UserLogin("LOGIN"))
                .type("TYPE")
                .name("NAME")
                .avatarUrl("https://avatars.githubusercontent.com/u/583231?v=4")
                .followersCount(1)
                .repositoriesCount(1)
                .createdAt(createdAt)
                .build();
        user.updateWithCalculation();

        //when
        UserResponse result = sut.userToUserResponse(user);

        //then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo("12");
        assertThat(result.login()).isEqualTo("LOGIN");
        assertThat(result.name()).isEqualTo("NAME");
        assertThat(result.type()).isEqualTo("TYPE");
        assertThat(result.avatarUrl()).isEqualTo("https://avatars.githubusercontent.com/u/583231?v=4");
        assertThat(result.createdAt()).isEqualTo("2020-01-01T01:01:01");
        assertThat(result.calculations()).isEqualTo("18.00");
    }
}