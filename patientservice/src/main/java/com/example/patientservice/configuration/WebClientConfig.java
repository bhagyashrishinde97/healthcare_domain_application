package com.example.patientservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final KeycloakProperties props;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(props.getBaseUrl())
                .build();
    }
}