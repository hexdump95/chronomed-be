package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.ProfileRequest;
import ar.sergiovillanueva.chronomed.dto.ProfileResponse;

public interface StaffProfileService {
    ProfileResponse getProfile(String userId);
    void updateProfile(String userId, ProfileRequest request);

}
