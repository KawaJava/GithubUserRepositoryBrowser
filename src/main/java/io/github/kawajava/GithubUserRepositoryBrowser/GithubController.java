package io.github.kawajava.GithubUserRepositoryBrowser;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GithubController {

    private final GithubService service;

    @GetMapping("/{username}")
    List<RepositoryResponse> repositories(@PathVariable String username) {
        return service.getUserRepositories(username);
    }
}

