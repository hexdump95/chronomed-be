package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.AvailabilityDetailResponse;
import ar.sergiovillanueva.chronomed.dto.AvailabilityRequest;
import ar.sergiovillanueva.chronomed.dto.AvailabilityResponse;
import ar.sergiovillanueva.chronomed.entity.Availability;
import ar.sergiovillanueva.chronomed.repository.AvailabilityRepository;
import ar.sergiovillanueva.chronomed.specification.AvailabilitySpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {
    private final Logger log = LoggerFactory.getLogger(AvailabilityServiceImpl.class);
    private final ObjectMapper objectMapper;
    private final AvailabilityRepository availabilityRepository;

    public AvailabilityServiceImpl(ObjectMapper objectMapper, AvailabilityRepository availabilityRepository) {
        this.objectMapper = objectMapper;
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public List<AvailabilityResponse> getAvailabilitiesByUserId(String userId) {
        log.debug("get availabilities by user id {}", userId);
        var specification = AvailabilitySpecification.byUserId(UUID.fromString(userId));
        var availabilities = availabilityRepository.findAll(specification);
        return availabilities.stream()
                .map(a -> {
                    var availabilityResponse = new AvailabilityResponse();
                    availabilityResponse.setId(a.getId());
                    availabilityResponse.setValidFrom(a.getValidFrom());
                    availabilityResponse.setValidTo(a.getValidTo());
                    return availabilityResponse;
                })
                .toList();
    }

    @Override
    public AvailabilityDetailResponse getAvailabilityByIdAndUserId(Long id, String userId) {
        log.debug("get availability by id {} and user id {}", id, userId);
        var specification =  AvailabilitySpecification.byId(id);
        specification = specification.and(AvailabilitySpecification.byUserId(UUID.fromString(userId)));

        var availability = availabilityRepository.findOne(specification)
                .orElseThrow(() -> new NotFoundServiceException("availability not found"));
        return objectMapper.convertValue(availability, AvailabilityDetailResponse.class);
    }

    @Override
    @Transactional
    public void createAvailability(String userId, AvailabilityRequest request) {
        log.debug("Create availability for user {}", userId);
        var availability = objectMapper.convertValue(request, Availability.class);
        availabilityRepository.save(availability);
    }

}
