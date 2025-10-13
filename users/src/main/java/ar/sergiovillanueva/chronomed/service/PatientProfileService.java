package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;

import java.util.List;

public interface PatientProfileService {
    PatientDetailResponse getProfile(String userId);

    void updateProfile(String userId, PatientRequest request);

    List<Long> getComorbidities(String patientId);

    void updateComorbidities(String patientId, List<Long> comorbidityIds);

    List<PatientInsuranceResponse> getInsurances(String patientId);

    Long createInsurance(String patientId, PatientInsuranceRequest request);

    void updateInsurance(Long id, String patientId, PatientInsuranceRequest request);

    void deleteInsurance(Long id, String patientId);

    List<SelectEntityResponse> getSex();

    List<SelectEntityResponse> getSelfPerceivedIdentities();

    List<SelectEntityResponse> getDocumentTypes();
}
