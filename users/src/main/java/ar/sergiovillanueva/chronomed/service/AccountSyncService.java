package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.KeycloakUser;
import ar.sergiovillanueva.chronomed.entity.Account;
import ar.sergiovillanueva.chronomed.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountSyncService {
    private final AccountRepository accountRepository;

    public AccountSyncService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account synchronizeBEAccount(KeycloakUser kcUser) {
        var account = new Account();
        account.setUserId(kcUser.getId());
        accountRepository.save(account);
        return account;
    }
}
