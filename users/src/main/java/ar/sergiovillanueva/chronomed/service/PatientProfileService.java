package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.PatientDetailResponse;
import ar.sergiovillanueva.chronomed.dto.PatientRequest;

public interface PatientProfileService {
    PatientDetailResponse getProfile(String userId);

    void updateProfile(String userId, PatientRequest request);
}
