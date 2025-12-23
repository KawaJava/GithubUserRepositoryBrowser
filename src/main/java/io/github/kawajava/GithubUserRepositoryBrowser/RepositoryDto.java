package io.github.kawajava.GithubUserRepositoryBrowser;

public record RepositoryDto(String name, Owner owner, boolean isFork) {
}
