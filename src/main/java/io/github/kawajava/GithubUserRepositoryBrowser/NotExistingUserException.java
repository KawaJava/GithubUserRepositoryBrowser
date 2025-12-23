package io.github.kawajava.GithubUserRepositoryBrowser;

public class NotExistingUserException extends RuntimeException {

    public NotExistingUserException(String username) {
        super("GitHub User: " + username + " Not Found!");
    }
}
