package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.SelectEntityResponse;

import java.util.List;

public interface DistrictService {
    List<SelectEntityResponse> findAllByProvinceId(Long provinceId);
}
