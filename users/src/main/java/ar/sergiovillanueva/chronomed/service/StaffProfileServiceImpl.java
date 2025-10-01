package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.ProfileRequest;
import ar.sergiovillanueva.chronomed.dto.ProfileResponse;
import ar.sergiovillanueva.chronomed.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class StaffProfileServiceImpl implements StaffProfileService {
    private final Logger log = LoggerFactory.getLogger(StaffProfileServiceImpl.class);
    private final AuthService authService;
    private final AccountRepository accountRepository;
    private final AccountSyncService profileSyncService;
    private final InsuranceLookupService insuranceLookupService;

    public StaffProfileServiceImpl(AuthService authService, AccountRepository accountRepository, AccountSyncService profileSyncService, InsuranceLookupService insuranceLookupService) {
        this.authService = authService;
        this.accountRepository = accountRepository;
        this.profileSyncService = profileSyncService;
        this.insuranceLookupService = insuranceLookupService;
    }

    @Override
    @Transactional
    public ProfileResponse getProfile(String userId) {
        var keycloakUser = authService.getUserById(UUID.fromString(userId));
        var profileResponse = new ProfileResponse();
        profileResponse.setFirstName(keycloakUser.getFirstName());
        profileResponse.setLastName(keycloakUser.getLastName());
        profileResponse.setEmail(keycloakUser.getEmail());
        profileResponse.setUsername(keycloakUser.getUsername());

        var profile = accountRepository.findById(UUID.fromString(userId))
                .orElseGet(() -> profileSyncService.synchronizeBEAccount(keycloakUser));
        profileResponse.setFileNumber(profile.getFileNumber());
        profileResponse.setPhoneNumber(profile.getPhoneNumber());
        profileResponse.setLocalityId(profile.getLocalityId());
        return profileResponse;
    }

    @Override
    @Transactional
    public void updateProfile(String userId, ProfileRequest request) {
        log.debug("Updating profile for user {}", userId);
        authService.updateEmail(UUID.fromString(userId), request.getEmail());
        var account = accountRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new NotFoundServiceException("user not found"));
        account.setPhoneNumber(request.getPhoneNumber());
        account.setLocalityId(request.getLocalityId());
        accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getInsuranceIds(String userId) {
        log.debug("Get insurance ids for user {}", userId);
        var account = accountRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new NotFoundServiceException("user not found"));
        return account.getInsuranceIds();
    }

    @Override
    @Transactional
    public void updateInsurances(String userId, List<Long> insuranceIds) {
        log.debug("Updating insurances for user {}", userId);
        var account = accountRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new NotFoundServiceException("user not found"));

        if (!insuranceIds.isEmpty()) {
            var existingInsurances = insuranceLookupService.verifyExistingIds(insuranceIds);
            if (!existingInsurances) {
                log.debug("insurance lookup failed");
                throw new RuntimeException("error with insurance lookup");
            }
        }

        account.setInsuranceIds(insuranceIds);
        accountRepository.save(account);
    }
}
