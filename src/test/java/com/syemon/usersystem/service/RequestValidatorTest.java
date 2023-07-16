package com.syemon.usersystem.service;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class RequestValidatorTest {

    @Autowired
    private RequestValidator sut;

    @Autowired
    private Validator validator;

    @ParameterizedTest
    @MethodSource("invalidUserQueryProvider")
    void getUser_shouldThrowException_whenNotValid(String login, String message) {
        Assertions.assertThatThrownBy(() -> sut.validate(new UserQuery(login)))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessage(message);
    }

    public static Stream<Arguments> invalidUserQueryProvider() {
        return Stream.of(
                Arguments.of("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",  "Error occurred: size must be between 1 and 39"),
                Arguments.of(null,                                        "Error occurred: must not be blank, must not be empty"),
                Arguments.of("",                                          "Error occurred: must not be blank, must not be empty, size must be between 1 and 39"),
                Arguments.of("                                        ",  "Error occurred: must not be blank, size must be between 1 and 39")
        );
    }

    @ParameterizedTest
    @MethodSource("validUserQueryProvider")
    void getUser_shouldNotThrowException_whenValid(String login) {
        Assertions.assertThatNoException()
                .isThrownBy(() -> sut.validate(new UserQuery(login)));

    }

    public static Stream<Arguments> validUserQueryProvider() {
        return Stream.of(
                Arguments.of("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"),
                Arguments.of("A")
        );
    }
}