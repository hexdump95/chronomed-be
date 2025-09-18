package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.ProfileRequest;
import ar.sergiovillanueva.chronomed.dto.ProfileResponse;

import java.util.List;

public interface StaffProfileService {
    ProfileResponse getProfile(String userId);

    void updateProfile(String userId, ProfileRequest request);

    List<Long> getInsuranceIds(String userId);

    void updateInsurances(String userId, List<Long> insuranceIds);

}
