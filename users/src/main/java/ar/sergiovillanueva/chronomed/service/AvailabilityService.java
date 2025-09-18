package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.AvailabilityRequest;
import ar.sergiovillanueva.chronomed.dto.AvailabilityResponse;

public interface AvailabilityService {
    AvailabilityResponse createAvailability(String userId, AvailabilityRequest request);
}
