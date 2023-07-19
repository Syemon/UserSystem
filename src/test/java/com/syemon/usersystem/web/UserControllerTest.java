package com.syemon.usersystem.web;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.syemon.usersystem.PostgresTestContainerResourceTest;
import com.syemon.usersystem.domain.UserLogin;
import com.syemon.usersystem.service.RequestLog;
import com.syemon.usersystem.service.RequestLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest extends PostgresTestContainerResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RequestLogRepository requestLogRepository;

    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("github.api.base-url", wireMock::baseUrl);
    }

    @Test
    void getUser_shouldReturn400_whenNotValid() throws Exception {
        //given
        String login = " ";
        assertThat(requestLogRepository.findByLogin(new UserLogin(login))).isEmpty();

        //when
        ResultActions resultActions = mockMvc.perform(
                get(UserController.BASE_PATH + "/" + login)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("""
                                {
                                   "code":"Bad Request",
                                   "message":"Error occurred: must not be blank"
                                }
                                """
                ));

        assertThat(requestLogRepository.findByLogin(new UserLogin(login))).isEmpty();
    }

    @Test
    void getUser_shouldReturn404_whenCouldNotFindUser() throws Exception {
        //given
        String login = "notfound";
        assertThat(requestLogRepository.findByLogin(new UserLogin(login))).isEmpty();

        //when
        ResultActions resultActions = mockMvc.perform(
                get(UserController.BASE_PATH + "/" + login)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json("""
                                {
                                   "code":"Not Found",
                                   "message":"Could not find user: notfound"
                                }
                                """
                ));

        Optional<RequestLog> resultLog = requestLogRepository.findByLogin(new UserLogin(login));
        assertThat(resultLog).isNotEmpty();
        assertThat(resultLog.get().getLogin()).isEqualTo(login);
        assertThat(resultLog.get().getRequestCount()).isEqualTo(1L);
    }

    @Test
    void getUser_shouldReturn200_whenFoundUser() throws Exception {
        //given
        String login = "Octocat";
        assertThat(requestLogRepository.findByLogin(new UserLogin(login))).isEmpty();

        //when
        ResultActions resultActions = mockMvc.perform(
                get(UserController.BASE_PATH + "/" + login)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                               {
                                   "id":"583231",
                                   "login":"octocat",
                                   "name":"The Octocat",
                                   "type":"User",
                                   "avatarUrl":"https://avatars.githubusercontent.com/u/583231?v=4",
                                   "createdAt":"2011-01-25T18:44:36",
                                   "calculations":"8.60"
                                }
                                """
                ));

        Optional<RequestLog> resultLog = requestLogRepository.findByLogin(new UserLogin(login));
        assertThat(resultLog).isNotEmpty();
        assertThat(resultLog.get().getLogin()).isEqualTo(login);
        assertThat(resultLog.get().getRequestCount()).isEqualTo(1L);
    }

    @Test
    void getUser_shouldReturn200AndIncrementRequestCount_whenGotMultipleCalls() throws Exception {
        //given
        String login = "multiple";
        assertThat(requestLogRepository.findByLogin(new UserLogin(login))).isEmpty();

        //when/then
        mockMvc.perform(
                get(UserController.BASE_PATH + "/" + login)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Optional<RequestLog> firstRequestLog = requestLogRepository.findByLogin(new UserLogin(login));
        assertThat(firstRequestLog).isPresent();
        assertThat(firstRequestLog.get().getRequestCount()).isEqualTo(1L);
        assertThat(firstRequestLog.get().getLogin()).isEqualTo(login);

        mockMvc.perform(
                get(UserController.BASE_PATH + "/" + login)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Optional<RequestLog> secondRequestLog = requestLogRepository.findByLogin(new UserLogin(login));
        assertThat(secondRequestLog).isPresent();
        assertThat(secondRequestLog.get().getRequestCount()).isEqualTo(2L);
        assertThat(secondRequestLog.get().getLogin()).isEqualTo(login);
    }

    @Test
    void getUser_shouldReturn404AndIncrementRequestCount_whenGotMultipleCallsButCouldNotFindUser() throws Exception {
        //given
        String login = "multipleNotFound";
        assertThat(requestLogRepository.findByLogin(new UserLogin(login))).isEmpty();

        //when/then
        mockMvc.perform(
                get(UserController.BASE_PATH + "/" + login)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Optional<RequestLog> firstRequestLog = requestLogRepository.findByLogin(new UserLogin(login));
        assertThat(firstRequestLog).isPresent();
        assertThat(firstRequestLog.get().getRequestCount()).isEqualTo(1L);
        assertThat(firstRequestLog.get().getLogin()).isEqualTo(login);

        mockMvc.perform(
                get(UserController.BASE_PATH + "/" + login)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Optional<RequestLog> secondRequestLog = requestLogRepository.findByLogin(new UserLogin(login));
        assertThat(secondRequestLog).isPresent();
        assertThat(secondRequestLog.get().getRequestCount()).isEqualTo(2L);
        assertThat(secondRequestLog.get().getLogin()).isEqualTo(login);
    }

}