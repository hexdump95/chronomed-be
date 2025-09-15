package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.InsuranceDetailResponse;
import ar.sergiovillanueva.chronomed.dto.InsuranceResponse;
import ar.sergiovillanueva.chronomed.dto.PageResponse;
import ar.sergiovillanueva.chronomed.dto.SelectEntityResponse;

import java.util.List;

public interface InsuranceService {
    PageResponse<InsuranceResponse> findInsurances(String name, int page);
    List<SelectEntityResponse> findAllByInsuranceTypeId(Long insuranceTypeId);
    InsuranceDetailResponse getOne(Long id);
}
