package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.KeycloakRole;
import ar.sergiovillanueva.chronomed.dto.KeycloakUserCreateRequest;
import ar.sergiovillanueva.chronomed.dto.KeycloakUser;
import ar.sergiovillanueva.chronomed.dto.PageResponse;

import java.util.List;
import java.util.UUID;

public interface AuthService {
    PageResponse<KeycloakUser> getUsers(int page);

    KeycloakUser getUserByEmail(String email);

    KeycloakUser createUser(KeycloakUserCreateRequest keycloakUserCreateRequest);

    List<KeycloakRole> getRolesByUserId(UUID userId);

    void assignRolesToUser(List<KeycloakRole> roles, UUID userId);

    List<KeycloakRole> getRoles();
}
