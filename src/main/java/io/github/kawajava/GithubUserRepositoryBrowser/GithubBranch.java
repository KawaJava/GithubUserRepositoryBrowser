package io.github.kawajava.GithubUserRepositoryBrowser;

public record GithubBranch(String name, Commit commit
) {
    public record Commit(String sha) {}
}

