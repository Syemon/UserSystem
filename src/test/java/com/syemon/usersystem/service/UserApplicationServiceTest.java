package com.syemon.usersystem.service;

import com.syemon.usersystem.domain.User;
import com.syemon.usersystem.domain.UserDomainException;
import com.syemon.usersystem.domain.UserLogin;
import com.syemon.usersystem.domain.UserNotFoundException;
import com.syemon.usersystem.domain.UserQueryRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserApplicationServiceTest {

    private final static UserQuery USER_QUERY = new UserQuery("login");

    @InjectMocks
    private UserApplicationService sut;

    @Mock
    private RequestValidator requestValidator;

    @Mock
    private UserQueryRepository userQueryRepository;

    @Mock
    private RequestLogRepository requestLogRepository;

    @Mock
    private UserMapper userMapper;

    @Spy
    private User user = User.builder().build();


    @Test
    void getUser_shouldThrowException_whenNotValid() {
        //given
        doThrow(new ConstraintViolationException(null))
        .when(requestValidator).validate(USER_QUERY);

        //when/then
        assertThatThrownBy(() -> sut.getUser(USER_QUERY))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void getUser_shouldThrowException_whenLogRepositoryFailed() {
        //given
        doThrow(new IllegalArgumentException("Test exception"))
                .when(requestLogRepository).findByLogin(new UserLogin(USER_QUERY.login()));

        //when/then
        assertThatThrownBy(() -> sut.getUser(USER_QUERY))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getUser_shouldThrowNotFoundException_whenCouldNotFindUser() {
        //given
        doThrow(new UserNotFoundException("Test exception"))
                .when(userQueryRepository).getUser(new UserLogin(USER_QUERY.login()));

        //when/then
        assertThatThrownBy(() -> sut.getUser(USER_QUERY))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void getUser_shouldUserDomainException_whenCreatedInvalidEntity() {
        //given
        when(userQueryRepository.getUser(new UserLogin(USER_QUERY.login()))).thenReturn(Optional.of(user));
        doThrow(new UserDomainException("Test exception")).when(user).validateInitialUser();

        //when/then
        assertThatThrownBy(() -> sut.getUser(USER_QUERY))
                .isInstanceOf(UserDomainException.class);
    }

    @Test
    void getUser_shouldCalculateAndCreateResponse_whenReceivedUser() {
        //given
        UserResponse userResponse = new UserResponse(
                "123",
                "login",
                "name",
                "type",
                "https://example/avatar.png",
                "2023-01-01T01:01:01.00Z",
                "23.33"
        );

        User user = User.builder().build();
        when(userQueryRepository.getUser(new UserLogin(USER_QUERY.login()))).thenReturn(Optional.of(user));
        when(userMapper.userToUserResponse(user)).thenReturn(userResponse);

        //when
        UserResponse result = sut.getUser(USER_QUERY);

        //then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(userResponse.id());
        assertThat(result.login()).isEqualTo(userResponse.login());
        assertThat(result.name()).isEqualTo(userResponse.name());
        assertThat(result.type()).isEqualTo(userResponse.type());
        assertThat(result.avatarUrl()).isEqualTo(userResponse.avatarUrl());
        assertThat(result.createdAt()).isEqualTo(userResponse.createdAt());
        assertThat(result.calculations()).isEqualTo(userResponse.calculations());
    }

}