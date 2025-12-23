# GithubUserRepositoryBrowser

## Description

Spring Boot application exposing a REST API that lists **non-fork GitHub repositories**
for a given user along with their branches and last commit SHA.

The application communicates with GitHub API, filters forked repositories
and handles error scenarios according to REST best practices.

---

## API Endpoint

### GET /{username}

#### Success – 200 OK

```json
[
  {
    "repositoryName": "my-repo",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "abc123"
      }
    ]
  }
]
```

#### User not found – 404

```json
{
  "status": 404,
  "message": "Github user 'ghost' not found"
}
```

---

## Technology stack

* Java 25
* Spring Boot 4.0.1
* RestClient
* Lombok
* WireMock
* WebTestClient
* Maven

---

## Architecture

* Service contains business logic
* Client handles HTTP communication
* Mapping logic is extracted to a dedicated mapper
* Global error handling via `@RestControllerAdvice`

---

## Testing

Tests cover:

* filtering forked repositories
* empty repository list
* non-existing user (404)

No real external HTTP calls are executed.

---

## Running tests

```bash
mvn clean test
```

---

## Notes

* No usage of `MockMvc`
* No usage of `Map`, `JsonNode`, or `Object`
* Immutable data structures (`record`)
* Stream API instead of imperative loops
* Java 25 features (`var`, `toList()`)
