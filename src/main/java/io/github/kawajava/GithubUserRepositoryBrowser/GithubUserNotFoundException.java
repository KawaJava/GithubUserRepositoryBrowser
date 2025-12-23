package io.github.kawajava.GithubUserRepositoryBrowser;

public class GithubUserNotFoundException extends RuntimeException {
    public GithubUserNotFoundException(String username) {
        super("Github user '%s' not found".formatted(username));
    }
}
