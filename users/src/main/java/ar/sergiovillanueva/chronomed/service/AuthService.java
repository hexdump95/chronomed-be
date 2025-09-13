package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;

import java.util.List;
import java.util.UUID;

public interface AuthService {
    PageResponse<KeycloakUser> getUsers(int page);

    KeycloakUser getUserById(UUID id);

    KeycloakUserDetail getUserDetailById(UUID id);

    KeycloakUser createUser(KeycloakUserCreateRequest request);

    KeycloakUser updateUser(UUID id, KeycloakUserUpdateRequest request);

    List<KeycloakRole> getRoles();
}
