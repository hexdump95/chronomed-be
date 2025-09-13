package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;

import java.util.List;

public interface FacilityService {
    PageResponse<FacilityResponse> findFacilities(String name, int page);

    List<SelectEntityResponse> findAllFacilities();

    FacilityDetailResponse findOne(Long id);

    FacilityResponse save(FacilityRequest request);

    FacilityResponse update(Long id, FacilityRequest request);

    void delete(Long id);
}
