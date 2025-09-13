package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.entity.Account;
import ar.sergiovillanueva.chronomed.entity.AccountRole;
import ar.sergiovillanueva.chronomed.mapper.AccountMapper;
import ar.sergiovillanueva.chronomed.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AuthService authService;
    private final AccountRepository accountRepository;
    private final SpecialtyLookupService specialtyLookupService;
    private final FacilityLookupService facilityLookupService;

    public AccountServiceImpl(AuthService authService, AccountRepository accountRepository, SpecialtyLookupService specialtyLookupService, FacilityLookupService facilityLookupService) {
        this.authService = authService;
        this.accountRepository = accountRepository;
        this.specialtyLookupService = specialtyLookupService;
        this.facilityLookupService = facilityLookupService;
    }

    @Override
    public PageResponse<KeycloakUser> findAll(int page) {
        return authService.getUsers(page);
    }

    @Override
    @Transactional
    public AccountResponse findById(UUID id) {
        var accountOpt = accountRepository.findById(id);
        var account = accountOpt.orElseGet(() -> synchronizeBEAccount(id));
        return AccountMapper.accountToAccountResponse(account, new AccountResponse());
    }

    @Override
    @Transactional
    public AccountResponse createAccount(AccountRequest request) {
        var keycloakUserRequest = AccountMapper.accountRequestToKeycloakUserCreateRequest(request, new KeycloakUserCreateRequest());
        var keycloakUser = authService.createUser(keycloakUserRequest);
        authService.addRolesToUser(request.getRoles(), keycloakUser.getId());

        var account = AccountMapper.accountRequestToAccount(request, new Account());
        account.setId(keycloakUser.getId());

        accountRepository.save(account);
        return AccountMapper.accountToAccountResponse(account, new AccountResponse());
    }

    @Override
    @Transactional
    public AccountResponse updateAccount(UUID id, AccountUpdateRequest request) {
        var account = accountRepository.findById(id).orElseThrow(() -> new NotFoundServiceException("account not found"));

        var keycloakUserRoles = authService.getRolesByUserId(id);
        List<KeycloakRole> rolesToAdd = new ArrayList<>();
        List<KeycloakRole> rolesToRemove = new ArrayList<>();
        request.getRoles().forEach(reqRole -> {
            if (!keycloakUserRoles.contains(reqRole))
                rolesToAdd.add(reqRole);
        });
        keycloakUserRoles.forEach(role -> {
            if (!request.getRoles().contains(role))
                rolesToRemove.add(role);
        });
        if (!rolesToAdd.isEmpty())
            authService.addRolesToUser(rolesToAdd, id);
        if (!rolesToRemove.isEmpty())
            authService.removeRolesToUser(rolesToRemove, id);

        if (!request.getFacilityIds().isEmpty()) {
            var existingFacilityIds = facilityLookupService.verifyExistingIds(request.getFacilityIds());
            if (!existingFacilityIds) {
                log.debug("facility lookup failed");
                throw new RuntimeException("error with facility lookup");
            }
        }

        if (!request.getSpecialtyIds().isEmpty()) {
            var existingSpecialties = specialtyLookupService.verifyExistingIds(request.getSpecialtyIds());
            if (!existingSpecialties) {
                log.debug("specialty lookup failed");
                throw new RuntimeException("error with specialty lookup");
            }
        }

        AccountMapper.accountUpdateRequestToAccount(request, account);
        accountRepository.save(account);
        return AccountMapper.accountToAccountResponse(account, new AccountResponse());
    }

    private Account synchronizeBEAccount(UUID id) {
        var account = new Account();
        var kcUser = authService.getUserById(id);

        account.setId(kcUser.getId());
        account.setEmail(kcUser.getEmail());
        account.setIdentityDocument(kcUser.getUsername());
        account.setFirstName(kcUser.getFirstName());
        account.setLastName(kcUser.getLastName());

        var keycloakUserRoles = authService.getRolesByUserId(id);
        var roles = keycloakUserRoles.stream().map(kr -> {
            var role = new AccountRole();
            role.setRoleId(kr.getId());
            return role;
        }).toList();

        account.setRoles(roles);
        accountRepository.save(account);
        return account;
    }

}
