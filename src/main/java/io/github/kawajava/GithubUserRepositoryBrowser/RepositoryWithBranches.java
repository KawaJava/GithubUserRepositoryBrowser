package io.github.kawajava.GithubUserRepositoryBrowser;

import java.util.List;

public record RepositoryWithBranches(String repositoryName, String ownerLogin, List<Branch> branches) {
}
