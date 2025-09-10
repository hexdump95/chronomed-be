package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.PageResponse;
import ar.sergiovillanueva.chronomed.dto.SpecialtyDetailResponse;
import ar.sergiovillanueva.chronomed.dto.SpecialtyRequest;
import ar.sergiovillanueva.chronomed.dto.SpecialtyResponse;

public interface SpecialtyService {
    PageResponse<SpecialtyResponse> findAll(String name, int page);
    SpecialtyDetailResponse findOne(Long id);
    SpecialtyResponse save(SpecialtyRequest request);
    SpecialtyResponse update(Long id, SpecialtyRequest request) throws Exception;
}
