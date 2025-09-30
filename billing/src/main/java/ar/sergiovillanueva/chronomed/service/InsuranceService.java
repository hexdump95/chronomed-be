package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;

import java.util.List;

public interface InsuranceService {
    PageResponse<InsuranceResponse> findInsurances(String search, int page);

    List<SelectEntityResponse> findAllByInsuranceTypeId(Long insuranceTypeId);

    InsuranceDetailResponse getOne(Long id);

    InsuranceResponse save(InsuranceRequest request);

    InsuranceResponse update(Long id, InsuranceRequest request);

    void delete(Long id);
}
