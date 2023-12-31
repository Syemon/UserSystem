package com.syemon.usersystem.dataaccess;

import com.syemon.usersystem.domain.User;
import com.syemon.usersystem.domain.UserId;
import com.syemon.usersystem.domain.UserLogin;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DataAccessUserMapperTest {

    private DataAccessUserMapper sut = new DataAccessUserMapper();

    @Test
    void githubUserToUser() {
        //when
        User result = sut.githubUserToUser(TestUtil.GITHUB_USER);

        //then
        assertThat(result.getId()).isEqualTo(new UserId(583231L));
        assertThat(result.getLogin()).isEqualTo(new UserLogin("octocat"));
        assertThat(result.getName()).isEqualTo("The Octocat");
        assertThat(result.getType()).isEqualTo("User");
        assertThat(result.getAvatarUrl()).isEqualTo("https://avatars.githubusercontent.com/u/583231?v=4");
        assertThat(result.getFollowersCount()).isEqualTo(9787L);
        assertThat(result.getRepositoriesCount()).isEqualTo(8L);
        assertThat(result.getCreatedAt()).isEqualTo(LocalDateTime.of(2011, 1, 25, 18, 44, 36));
        assertThat(result.getCalculations()).isEmpty();
    }
}