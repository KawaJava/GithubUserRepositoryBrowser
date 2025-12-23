package io.github.kawajava.GithubUserRepositoryBrowser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class RestClientConfig {

    @Bean
    RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Value("${github.api.base.url}")
    private String githubApiBaseUrl;

    @Bean
    RestClient githubRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl(githubApiBaseUrl)
                .build();
    }
}
