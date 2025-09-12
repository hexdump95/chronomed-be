package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.KeycloakRole;

import java.util.List;

public interface RoleService {
    List<KeycloakRole> getRoles();
}
