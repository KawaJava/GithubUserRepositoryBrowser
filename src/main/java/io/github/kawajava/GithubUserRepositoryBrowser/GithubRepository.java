package io.github.kawajava.GithubUserRepositoryBrowser;

public record GithubRepository(String name, boolean fork, Owner owner
) {
    public record Owner(String login) {}
}
