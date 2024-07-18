package com.project.utils;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public final class SecurityUtils {
    private SecurityUtils() {
    }

    public static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        // Vous pouvez accéder à des informations spécifiques sur l'utilisateur connecté à partir de securityContext
        return Optional
                .ofNullable(securityContext.getAuthentication())
                .filter(authentication -> authentication.getName() instanceof String)
                .map(authentication -> authentication.getName());
    }

    public static Optional<String> getCurrentRoleJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        // Vous pouvez accéder à des informations spécifiques sur l'utilisateur connecté à partir de securityContext
        return Optional
                .ofNullable(securityContext.getAuthentication())
                .filter(authentication -> authentication.getAuthorities().stream().findFirst().get().getAuthority() instanceof String)
                .map(authentication -> authentication.getAuthorities().stream().findFirst().get().getAuthority());
    }
}
