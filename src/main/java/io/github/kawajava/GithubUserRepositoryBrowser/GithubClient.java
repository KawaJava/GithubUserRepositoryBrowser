package io.github.kawajava.GithubUserRepositoryBrowser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class GithubClient {

    private final RestClient restClient;

    public List<GithubRepository> getRepositories(String username) {

        log.info("Fetching repositories for user: {}", username);

        return Optional.ofNullable(
                restClient.get()
                        .uri("/users/{username}/repos", username)
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError,
                                (req, res) -> {
                                    log.warn("User '{}' does not exist. Throwing exception.", username);
                                    throw new NotExistingUserException(username);
                                })
                        .body(new ParameterizedTypeReference<List<GithubRepository>>() {})
        ).orElseThrow(() -> new IllegalStateException("GitHub API returned null repositories"));
    }

    public List<GithubBranch> getBranches(String username, String repo) {

        log.info("Fetching branches for repository '{}' of user '{}'", repo, username);

        return Optional.ofNullable(
                restClient.get()
                        .uri("/repos/{username}/{repo}/branches", username, repo)
                        .retrieve()
                        .body(new ParameterizedTypeReference<List<GithubBranch>>() {})
        ).orElseThrow(() -> new IllegalStateException("GitHub API returned null branches"));
    }
}

