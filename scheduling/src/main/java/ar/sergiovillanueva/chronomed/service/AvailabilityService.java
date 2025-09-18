package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.AvailabilityDetailResponse;
import ar.sergiovillanueva.chronomed.dto.AvailabilityRequest;
import ar.sergiovillanueva.chronomed.dto.AvailabilityResponse;
import ar.sergiovillanueva.chronomed.dto.AvailabilityUpdateRequest;

import java.util.List;

public interface AvailabilityService {
    List<AvailabilityResponse> getAvailabilitiesByUserId(String userId);

    AvailabilityDetailResponse getAvailabilityByIdAndUserId(Long id, String userId);

    void createAvailability(String userId, AvailabilityRequest request);

    void updateAvailabilityDaysByUserId(Long id, String userId, AvailabilityUpdateRequest request);
}
