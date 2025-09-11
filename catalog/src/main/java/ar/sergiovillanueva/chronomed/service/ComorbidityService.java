package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;

public interface ComorbidityService {
    PageResponse<ComorbidityResponse> findAll(String name, int page);

    ComorbidityResponse findOne(Long id);

    ComorbidityResponse save(ComorbidityRequest request);

    ComorbidityResponse update(Long id, ComorbidityRequest request);

    void delete(Long id);
}
