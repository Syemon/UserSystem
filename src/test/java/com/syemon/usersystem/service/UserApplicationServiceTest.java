package com.syemon.usersystem.service;

import com.syemon.usersystem.domain.UserLogin;
import com.syemon.usersystem.domain.UserNotFoundException;
import com.syemon.usersystem.domain.UserQueryRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserApplicationServiceTest {

    private final static UserQuery USER_QUERY = new UserQuery("login");

    @InjectMocks
    private UserApplicationService sut;

    @Mock
    private RequestValidator requestValidator;

    @Mock
    private UserQueryRepository userQueryRepository;

    @Test
    void getUser_shouldThrowException_whenNotValid() {
        //given
        Mockito.doThrow(new ConstraintViolationException(null))
        .when(requestValidator).validate(USER_QUERY);

        //when/then
        assertThatThrownBy(() -> sut.getUser(USER_QUERY))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void getUser_shouldThrowNotFoundException_whenCouldNotFindUser() {
        //given
        Mockito.doThrow(new UserNotFoundException("Test exception"))
                .when(userQueryRepository).getUser(new UserLogin(USER_QUERY.login()));

        //when/then
        assertThatThrownBy(() -> sut.getUser(USER_QUERY))
                .isInstanceOf(UserNotFoundException.class);
    }

}