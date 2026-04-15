package com.example.patientservice.utility;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtHelper {

    private static Jwt getJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new RuntimeException("No valid JWT found in security context");
        }
        return jwt;
    }

    public static UUID getCurrentUserId() {
        String sub = getJwt().getSubject();
        return UUID.fromString(sub);
    }

    public static String getCurrentUserEmail() {
        return  getJwt().getClaimAsString("email");
    }

    public static String getCurrentUserFirstName() {
        return getJwt().getClaimAsString("given_name");
    }

    public static String getCurrentUserLastName() {
        return getJwt().getClaimAsString("family_name");
    }
}

