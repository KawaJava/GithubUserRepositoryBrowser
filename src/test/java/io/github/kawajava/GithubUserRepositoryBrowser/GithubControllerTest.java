package io.github.kawajava.GithubUserRepositoryBrowser;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubIntegrationTest {

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void startWireMock() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
        wireMockServer.start();
    }

    @AfterAll
    static void stopWireMock() {
        wireMockServer.stop();
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("github.api.base-url", wireMockServer::baseUrl);
    }

    @Autowired
    WebTestClient webTestClient;

    @Test
    void shouldReturnOnlyNonForkRepositories() {
        stubFor(get(urlEqualTo("/users/testuser/repos"))
                .willReturn(okJson("""
                    [
                      { "name": "fork", "fork": true, "owner": { "login": "testuser" } },
                      { "name": "repo", "fork": false, "owner": { "login": "testuser" } }
                    ]
                """)));

        stubFor(get(urlEqualTo("/repos/testuser/repo/branches"))
                .willReturn(okJson("""
                    [
                      { "name": "main", "commit": { "sha": "abc123" } }
                    ]
                """)));

        webTestClient.get()
                .uri("/api/github/testuser/repositories")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("""
                    [
                      {
                        "repositoryName": "repo",
                        "ownerLogin": "testuser",
                        "branches": [
                          { "name": "main", "lastCommitSha": "abc123" }
                        ]
                      }
                    ]
                """);
    }

    @Test
    void shouldReturnEmptyListWhenUserHasNoRepositories() {
        stubFor(get(urlEqualTo("/users/empty/repos"))
                .willReturn(okJson("[]")));

        webTestClient.get()
                .uri("/api/github/empty/repositories")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("[]");
    }

    @Test
    void shouldReturn404WhenUserNotFound() {
        stubFor(get(urlEqualTo("/users/ghost/repos"))
                .willReturn(notFound()));

        webTestClient.get()
                .uri("/api/github/ghost/repositories")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .json("""
                    {
                      "status": 404,
                      "message": "Github user 'ghost' not found"
                    }
                """);
    }
}
