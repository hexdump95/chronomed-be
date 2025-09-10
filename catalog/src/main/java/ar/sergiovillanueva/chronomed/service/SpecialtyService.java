package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.PageResponse;
import ar.sergiovillanueva.chronomed.dto.SpecialtyResponse;

public interface SpecialtyService {
    PageResponse<SpecialtyResponse> findAll(String query, int page);
}
