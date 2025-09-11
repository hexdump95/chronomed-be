package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.SpecialtyPriceRequest;
import ar.sergiovillanueva.chronomed.dto.SpecialtyPriceResponse;

public interface SpecialtyPriceService {
    SpecialtyPriceResponse save(Long specialtyId, SpecialtyPriceRequest request);
}
