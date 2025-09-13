package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;

import java.util.List;

public interface SpecialtyService {
    PageResponse<SpecialtyResponse> findSpecialties(String name, int page);

    List<SelectEntityResponse> findAllSpecialties();

    SpecialtyDetailResponse findOne(Long id);

    SpecialtyResponse save(SpecialtyRequest request);

    SpecialtyResponse update(Long id, SpecialtyRequest request);

    void delete(Long id);
}
