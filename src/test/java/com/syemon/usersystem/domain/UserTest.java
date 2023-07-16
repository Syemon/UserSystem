package com.syemon.usersystem.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    private final static UserId USER_ID = new UserId(1L);
    private final static String LOGIN = "LOGIN";
    private final static String TYPE = "TYPE";
    private final static String AVATAR_URL = "https://example.com/avatar.png";
    private final static Long FOLLOWERS_COUNT = 11L;
    private final static Long REPOSITORIES_COUNT = 2L;
    private final static LocalDateTime CREATED_AT = LocalDateTime.of(2000, 12, 1, 1, 1);

    @Test
    public void initializeUser() {
        //when
        User user = User.builder()
                .id(USER_ID)
                .login(LOGIN)
                .type(TYPE)
                .avatarUrl(AVATAR_URL)
                .followersCount(FOLLOWERS_COUNT)
                .repositoriesCount(REPOSITORIES_COUNT)
                .createdAt(CREATED_AT)
                .build();

        //then
        assertThat(user.getId()).isEqualTo(USER_ID);
        assertThat(user.getLogin()).isEqualTo(LOGIN);
        assertThat(user.getType()).isEqualTo(TYPE);
        assertThat(user.getAvatarUrl()).isEqualTo(AVATAR_URL);
        assertThat(user.getFollowersCount()).isEqualTo(FOLLOWERS_COUNT);
        assertThat(user.getRepositoriesCount()).isEqualTo(REPOSITORIES_COUNT);
        assertThat(user.getCreatedAt()).isEqualTo(CREATED_AT);
    }

    @ParameterizedTest
    @MethodSource("calculateProvider")
    public void calculate(long followersCount, long repositoriesCount, BigDecimal expectedResult) {
        //given
        User user = User.builder()
                .id(USER_ID)
                .login(LOGIN)
                .type(TYPE)
                .avatarUrl(AVATAR_URL)
                .followersCount(followersCount)
                .repositoriesCount(repositoriesCount)
                .createdAt(CREATED_AT)
                .build();

        //when/then
        assertThat(user.calculate()).isEqualTo(Optional.ofNullable(expectedResult));
    }

    public static Stream<Arguments> calculateProvider() {
        return Stream.of(
                Arguments.of(1L,    1L,     "18.00"),
                Arguments.of(2L,    2L,     "12.00"),
                Arguments.of(3L,    4L,     "12.00"),
                Arguments.of(13L,   33L,    "16.10"),
                Arguments.of(6L,    0L,     "2.00"),
                Arguments.of(0L,    2L,     null)
        );
    }

    @ParameterizedTest
    @MethodSource("calculateNegativeNumbersProvider")
    public void calculate_shouldThrowException_whenSetWithNegativeNumbers(long followersCount, long repositoriesCount) {
        //given
        User user = User.builder()
                .id(USER_ID)
                .login(LOGIN)
                .type(TYPE)
                .avatarUrl(AVATAR_URL)
                .followersCount(followersCount)
                .repositoriesCount(repositoriesCount)
                .createdAt(CREATED_AT)
                .build();

        //when/then
        Assertions.assertThatThrownBy(user::calculate)
                .isInstanceOf(UserDomainException.class)
                .hasMessage("Received incorrect values. Followers count and repositories count cannot be a negative number");
    }

    public static Stream<Arguments> calculateNegativeNumbersProvider() {
        return Stream.of(
                Arguments.of(-1L,   2L),
                Arguments.of( 2L,   -1L),
                Arguments.of(-1L,   -1L)
        );
    }
}