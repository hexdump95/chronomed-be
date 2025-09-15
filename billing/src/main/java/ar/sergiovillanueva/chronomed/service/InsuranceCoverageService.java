package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.InsuranceCoverageRequest;
import ar.sergiovillanueva.chronomed.dto.InsuranceCoverageResponse;

public interface InsuranceCoverageService {
    InsuranceCoverageResponse save(Long insuranceId, InsuranceCoverageRequest request);
    InsuranceCoverageResponse getOne(Long id);
}
