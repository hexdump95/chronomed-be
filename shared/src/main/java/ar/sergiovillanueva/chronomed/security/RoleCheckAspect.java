package ar.sergiovillanueva.chronomed.security;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect
@Component
public class RoleCheckAspect {

    @Value("${authservice.resource-client}")
    private String resourceClient;

    @Before("@annotation(rolesRequired)")
    public void checkRolesRequired(RolesRequired rolesRequired) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AccessDeniedException("Authentication is null");
        }

        List<String> requiredRoles = Arrays.stream(rolesRequired.value()).toList();

        Map<String, Object> resourceAccess = ((Jwt) authentication.getPrincipal()).getClaimAsMap("resource_access");
        var clientObj = resourceAccess.get(resourceClient);
        if(clientObj == null) {
            throw new AccessDeniedException("Resource client is null");
        }
        var client = (LinkedTreeMap<?, ?>) clientObj;
        List<String> roles = (ArrayList<String>) client.get("roles");

        boolean hasRole = roles.stream().anyMatch(requiredRoles::contains);
        if (!hasRole) {
            throw new AccessDeniedException("Roles not found");
        }
    }
}
