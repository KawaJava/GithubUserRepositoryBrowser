package io.github.kawajava.GithubUserRepositoryBrowser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GithubService {

    private final GithubClient client;
    private final GithubRepositoryMapper mapper;

    public List<RepositoryResponse> getUserRepositories(String username) {
        log.info("Fetching repositories for user={}", username);

        return client.getRepositories(username).stream()
                .filter(repo -> !repo.fork())
                .map(repo -> {
                    var branches = client.getBranches(username, repo.name());
                    return mapper.toResponse(repo, branches);
                })
                .toList();
    }
}
