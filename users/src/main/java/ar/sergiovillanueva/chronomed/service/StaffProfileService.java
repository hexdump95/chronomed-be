package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.ProfileResponse;

public interface StaffProfileService {
    ProfileResponse getProfile(String username);
}
