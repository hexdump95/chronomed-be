package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.ProfileRequest;
import ar.sergiovillanueva.chronomed.dto.ProfileResponse;
import ar.sergiovillanueva.chronomed.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class StaffProfileServiceImpl implements StaffProfileService {
    private final Logger log = LoggerFactory.getLogger(StaffProfileServiceImpl.class);
    private final AuthService authService;
    private final AccountRepository accountRepository;
    private final AccountSyncService profileSyncService;

    public StaffProfileServiceImpl(AuthService authService, AccountRepository accountRepository, AccountSyncService profileSyncService) {
        this.authService = authService;
        this.accountRepository = accountRepository;
        this.profileSyncService = profileSyncService;
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

}
