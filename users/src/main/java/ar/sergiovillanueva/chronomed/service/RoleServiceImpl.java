package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.KeycloakRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RoleServiceImpl implements RoleService {
    private final AuthService authService;
    private static final String EXCLUDED_ROLE = "uma_protection";

    public RoleServiceImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public List<KeycloakRole> getRoles() {
        var getRoleByNameResponse = authService.getRoles();

        return getRoleByNameResponse
                .stream()
                .filter(kr -> !Objects.equals(kr.getName(), EXCLUDED_ROLE))
                .toList();
    }

}
