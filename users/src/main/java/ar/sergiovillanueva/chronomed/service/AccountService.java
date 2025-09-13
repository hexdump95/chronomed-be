package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;

import java.util.UUID;

public interface AccountService {
    AccountResponse findByUserId(UUID id);
    AccountResponse updateAccount(UUID id, AccountUpdateRequest request);
}
