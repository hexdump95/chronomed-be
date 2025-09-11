package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.FacilityDetailResponse;
import ar.sergiovillanueva.chronomed.dto.FacilityRequest;
import ar.sergiovillanueva.chronomed.dto.FacilityResponse;
import ar.sergiovillanueva.chronomed.dto.PageResponse;

public interface FacilityService {
    PageResponse<FacilityResponse> findAll(String name, int page);

    FacilityDetailResponse findOne(Long id);

    FacilityResponse save(FacilityRequest request);

    FacilityResponse update(Long id, FacilityRequest request);

    void delete(Long id);
}
