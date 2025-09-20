package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.PatientDetailResponse;
import ar.sergiovillanueva.chronomed.dto.PatientInsuranceRequest;
import ar.sergiovillanueva.chronomed.dto.PatientInsuranceResponse;
import ar.sergiovillanueva.chronomed.dto.PatientRequest;

import java.util.List;

public interface PatientProfileService {
    PatientDetailResponse getProfile(String userId);

    void updateProfile(String userId, PatientRequest request);

    List<Long> getComorbidities(String patientId);

    void updateComorbidities(String patientId, List<Long> comorbidityIds);

    List<PatientInsuranceResponse> getInsurances(String patientId);

    void updateInsurances(String patientId, List<PatientInsuranceRequest> comorbidityIds);
}
