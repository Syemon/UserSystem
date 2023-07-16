package com.syemon.usersystem.service;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubClientTest {

    @Autowired
    private GithubClient sut;

    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("github.api.base-url", wireMock::baseUrl);
    }

    @Test
    void getUser() {
        //when
        GithubUser result = sut.getUser("Octocat");

        //then
        assertThat(result.login()).isEqualTo("octocat");
        assertThat(result.id()).isEqualTo(583231);
        assertThat(result.node_id()).isEqualTo("MDQ6VXNlcjU4MzIzMQ==");
        assertThat(result.avatar_url()).isEqualTo("https://avatars.githubusercontent.com/u/583231?v=4");
        assertThat(result.gravatar_id()).isEqualTo("");
        assertThat(result.url()).isEqualTo("https://api.github.com/users/octocat");
        assertThat(result.html_url()).isEqualTo("https://github.com/octocat");
        assertThat(result.followers_url()).isEqualTo("https://api.github.com/users/octocat/followers");
        assertThat(result.following_url()).isEqualTo("https://api.github.com/users/octocat/following{/other_user}");
        assertThat(result.gists_url()).isEqualTo("https://api.github.com/users/octocat/gists{/gist_id}");
        assertThat(result.starred_url()).isEqualTo("https://api.github.com/users/octocat/starred{/owner}{/repo}");
        assertThat(result.subscriptions_url()).isEqualTo("https://api.github.com/users/octocat/subscriptions");
        assertThat(result.organizations_url()).isEqualTo("https://api.github.com/users/octocat/orgs");
        assertThat(result.repos_url()).isEqualTo("https://api.github.com/users/octocat/repos");
        assertThat(result.events_url()).isEqualTo("https://api.github.com/users/octocat/events{/privacy}");
        assertThat(result.received_events_url()).isEqualTo("https://api.github.com/users/octocat/received_events");
        assertThat(result.type()).isEqualTo("User");
        assertThat(result.site_admin()).isEqualTo(false);
        assertThat(result.name()).isEqualTo("The Octocat");
        assertThat(result.company()).isEqualTo("@github");
        assertThat(result.blog()).isEqualTo("https://github.blog");
        assertThat(result.location()).isEqualTo("San Francisco");
        assertThat(result.email()).isEqualTo(null);
        assertThat(result.hireable()).isEqualTo(null);
        assertThat(result.bio()).isEqualTo(null);
        assertThat(result.twitter_username()).isEqualTo(null);
        assertThat(result.public_repos()).isEqualTo(8);
        assertThat(result.public_gists()).isEqualTo(8);
        assertThat(result.followers()).isEqualTo(9787);
        assertThat(result.following()).isEqualTo(9);
        assertThat(result.created_at()).isEqualTo("2011-01-25T18:44:36Z");
        assertThat(result.updated_at()).isEqualTo("2023-06-22T11:15:59Z");
    }
}