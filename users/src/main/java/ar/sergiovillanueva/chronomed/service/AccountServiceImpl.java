package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.entity.Account;
import ar.sergiovillanueva.chronomed.mapper.AccountMapper;
import ar.sergiovillanueva.chronomed.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AuthService authService;
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AuthService authService, AccountRepository accountRepository) {
        this.authService = authService;
        this.accountRepository = accountRepository;
    }

    @Override
    public PageResponse<KeycloakUser> findAll(int page) {
        return authService.getUsers(page);
    }

    @Override
    @Transactional
    public AccountResponse createAccount(AccountRequest request) {
        var keycloakUserRequest = AccountMapper.accountRequestToKeycloakUserCreateRequest(request, new KeycloakUserCreateRequest());
        var keycloakUser = authService.createUser(keycloakUserRequest);
        authService.assignRolesToUser(request.getRoles(), keycloakUser.getId());

        var account = AccountMapper.accountRequestToAccount(request, new Account());
        account.setId(keycloakUser.getId());

        accountRepository.save(account);
        return AccountMapper.accountToAccountResponse(account, new AccountResponse());
    }

}
