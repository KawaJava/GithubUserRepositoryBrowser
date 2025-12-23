package io.github.kawajava.GithubUserRepositoryBrowser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class RestClientConfig {

    @Bean
    RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    RestClient githubRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("https://api.github.com")
                .build();
    }
}
