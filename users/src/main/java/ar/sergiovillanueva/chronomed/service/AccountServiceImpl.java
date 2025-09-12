package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.KeycloakRole;
import ar.sergiovillanueva.chronomed.dto.KeycloakUser;
import ar.sergiovillanueva.chronomed.dto.PageResponse;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final AuthService<KeycloakUser, KeycloakRole> authService;

    public AccountServiceImpl(AuthService<KeycloakUser, KeycloakRole> authService) {
        this.authService = authService;
    }

    @Override
    public PageResponse<KeycloakUser> findAll(int page) {
        return authService.getUsers(page);
    }
}
