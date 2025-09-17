package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.mapper.AccountMapper;
import ar.sergiovillanueva.chronomed.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AuthService authService;
    private final AccountRepository accountRepository;
    private final SpecialtyLookupService specialtyLookupService;
    private final FacilityLookupService facilityLookupService;
    private final AccountSyncService profileSyncService;

    public AccountServiceImpl(AuthService authService, AccountRepository accountRepository, SpecialtyLookupService specialtyLookupService, FacilityLookupService facilityLookupService, AccountSyncService profileSyncService) {
        this.authService = authService;
        this.accountRepository = accountRepository;
        this.specialtyLookupService = specialtyLookupService;
        this.facilityLookupService = facilityLookupService;
        this.profileSyncService = profileSyncService;
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse findByUserId(UUID id) {
        var accountOpt = accountRepository.findById(id);
        var keycloakUser = authService.getUserById(id);
        var account = accountOpt.orElseGet(() -> profileSyncService.synchronizeBEAccount(keycloakUser));
        return AccountMapper.accountToAccountResponse(account, new AccountResponse());
    }

    @Override
    @Transactional
    public AccountResponse updateAccount(UUID id, AccountUpdateRequest request) {
        var account = accountRepository.findById(id).orElseThrow(() -> new NotFoundServiceException("account not found"));

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

}
