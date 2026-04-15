package com.example.patientservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "keycloak")
@Data
public class KeycloakProperties {
    private String baseUrl;
    private String realm;
    private String adminUsername;
    private String adminPassword;
    private String clientId;
}