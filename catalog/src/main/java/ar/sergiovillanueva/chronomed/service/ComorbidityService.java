package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;

import java.util.List;

public interface ComorbidityService {
    PageResponse<ComorbidityResponse> findComorbidities(String name, int page);

    List<SelectEntityResponse> findAllComorbidities();

    ComorbidityResponse findOne(Long id);

    ComorbidityResponse save(ComorbidityRequest request);

    ComorbidityResponse update(Long id, ComorbidityRequest request);

    void delete(Long id);
}
