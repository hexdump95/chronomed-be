package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.SelectEntityResponse;

import java.util.List;

public interface ProvinceService {
    List<SelectEntityResponse> findAll();
}
