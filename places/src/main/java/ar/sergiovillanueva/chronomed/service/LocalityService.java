package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.LocalityResponse;
import ar.sergiovillanueva.chronomed.dto.SelectEntityResponse;

import java.util.List;

public interface LocalityService {
    List<SelectEntityResponse> findAllByDistrictId(Long districtId);
    LocalityResponse getLocality(Long id);
}
