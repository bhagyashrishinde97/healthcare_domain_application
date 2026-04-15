package com.example.patientservice.serviceimpl;

import com.example.patientservice.configuration.KeycloakProperties;
import com.example.patientservice.dto.response.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final WebClient webClient;
    private final KeycloakProperties props;

    /**
     * Get admin token from master realm
     */
    public String getAdminToken() {
        return webClient.post()
                .uri("/realms/master/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("client_id", "admin-cli")
                        .with("username", props.getAdminUsername())
                        .with("password", props.getAdminPassword())
                        .with("grant_type", "password"))
                .retrieve()
                .bodyToMono(Map.class)
                .map(res -> (String) res.get("access_token"))
                .block();
    }

    /**
     * Create user in Keycloak, return Keycloak user UUID
     */
    public String createUser(String token, String firstname, String lastname, String username, String password, String email) {
        Map<String, Object> user = Map.of(
                    "firstName", firstname,
                "lastName", lastname,
                "username", username,
                "enabled", true,
                "email", email,
                "credentials", List.of(
                        Map.of("type", "password", "value", password, "temporary", false)
                )
        );

        webClient.post()
                .uri("/admin/realms/" + props.getRealm() + "/users")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .retrieve()
                .toBodilessEntity()
                .block();

        // Fetch the created user's ID
        List<Map<String, Object>> users = webClient.get()
                .uri(uri -> uri
                        .path("/admin/realms/" + props.getRealm() + "/users")
                        .queryParam("username", username)
                        .build())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(List.class)
                .block();

        if (users == null || users.isEmpty()) {
            throw new RuntimeException("User not found in Keycloak after creation");
        }
        return (String) users.get(0).get("id");
    }

    /**
     * Assign a realm-level role to a Keycloak user
     */
    public void assignRole(String token, String userId, String roleName) {
        Map<?, ?> role = webClient.get()
                .uri("/admin/realms/" + props.getRealm() + "/roles/" + roleName)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        assert role != null;
        webClient.post()
                .uri("/admin/realms/" + props.getRealm() + "/users/" + userId + "/role-mappings/realm")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(List.of(role))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    /**
     * Password-grant login for a user
     */
    public LoginResponseDto login(String username, String password) {
        return webClient.post()
                .uri("/realms/" + props.getRealm() + "/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("client_id", props.getClientId())
                                .with("grant_type", "password")
                                .with("username", username)
                                .with("password", password)
                )
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class).flatMap(errorBody -> {
                            System.err.println("!!! KEYCLOAK RAW ERROR: " + errorBody);
                            return Mono.error(new RuntimeException("Keycloak Error: " + errorBody));
                        })
                )
                .bodyToMono(LoginResponseDto.class)
                .block();
    }

    /**
     * Sets the email_verified attribute to true for a specific user UUID
     */
    public void verifyUserEmail(String token, UUID userId) {
        Map<String, Object> update = Map.of("emailVerified", true);

        webClient.put()
                .uri("/admin/realms/" + props.getRealm() + "/users/" + userId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(update)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new RuntimeException("Failed to verify email for user: " + userId)))
                .toBodilessEntity()
                .block();
    }
}