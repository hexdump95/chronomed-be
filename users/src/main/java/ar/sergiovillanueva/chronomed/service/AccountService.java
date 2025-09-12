package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.KeycloakUser;
import ar.sergiovillanueva.chronomed.dto.PageResponse;

public interface AccountService {
    PageResponse<KeycloakUser> findAll(int page);
}
