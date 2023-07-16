package com.syemon.usersystem.service;

import com.syemon.usersystem.domain.User;
import com.syemon.usersystem.domain.UserDomainException;
import com.syemon.usersystem.domain.UserLogin;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GithubUserQueryRepositoryTest {

    public static final UserLogin USER_LOGIN = new UserLogin("user");
    @InjectMocks
    private GithubUserQueryRepository sut;

    @Mock
    private GithubClient githubClient;

    @Mock
    private UserMapper userMapper;

    @Test
    void getUser_shouldReturnEmptyOptional_whenUserWasNotFound() {
        //given
        when(githubClient.getUser(USER_LOGIN)).thenReturn(Optional.empty());

        //when/then
        assertThat(sut.getUser(USER_LOGIN)).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("githubClientExceptionProvider")
    void getUser_shouldThrowException_whenReceivedUnexpectedClientError(Exception exception) {
        //given
        when(githubClient.getUser(USER_LOGIN)).thenThrow(exception);

        //when/then
        assertThatThrownBy(() -> sut.getUser(USER_LOGIN))
                .isInstanceOf(UserDomainException.class);
    }

    public static Stream<Arguments> githubClientExceptionProvider() {
        return Stream.of(
                Arguments.of(new GithubClientException("Test exception")),
                Arguments.of(new RuntimeException("Test exception"))
        );
    }

    @Test
    void getUser() {
        //given
        User expectedUser = User.builder().build();

        when(githubClient.getUser(USER_LOGIN)).thenReturn(Optional.of(ServiceTestUtil.GITHUB_USER));
        when(userMapper.githubUserToUser(ServiceTestUtil.GITHUB_USER)).thenReturn(expectedUser);

        //when
        Optional<User> result = sut.getUser(USER_LOGIN);

        //then
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(expectedUser);
    }
}