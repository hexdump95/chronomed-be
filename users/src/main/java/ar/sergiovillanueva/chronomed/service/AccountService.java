package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;

import java.util.UUID;

public interface AccountService {
    PageResponse<KeycloakUser> findAll(int page);
    AccountResponse findById(UUID id);
    AccountResponse createAccount(AccountRequest request);
    AccountResponse updateAccount(UUID id, AccountUpdateRequest request);
}
