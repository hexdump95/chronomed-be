package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.ProfileResponse;
import ar.sergiovillanueva.chronomed.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class StaffProfileServiceImpl implements StaffProfileService {
    private final AuthService authService;
    private final AccountRepository accountRepository;
    private final AccountSyncService profileSyncService;

    public StaffProfileServiceImpl(AuthService authService, AccountRepository accountRepository, AccountSyncService profileSyncService) {
        this.authService = authService;
        this.accountRepository = accountRepository;
        this.profileSyncService = profileSyncService;
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileResponse getProfile(String username) {
        var keycloakUser = authService.getUserById(UUID.fromString(username));
        var profileResponse = new ProfileResponse();
        profileResponse.setFirstName(keycloakUser.getFirstName());
        profileResponse.setLastName(keycloakUser.getLastName());
        profileResponse.setEmail(keycloakUser.getEmail());
        profileResponse.setUsername(keycloakUser.getUsername());

        var profile = accountRepository.findById(UUID.fromString(username))
                .orElseGet(() -> profileSyncService.synchronizeBEAccount(keycloakUser));
        profileResponse.setFileNumber(profile.getFileNumber());
        profileResponse.setPhoneNumber(profile.getPhoneNumber());
        profileResponse.setLocalityId(profile.getLocalityId());
        return profileResponse;
    }

}
