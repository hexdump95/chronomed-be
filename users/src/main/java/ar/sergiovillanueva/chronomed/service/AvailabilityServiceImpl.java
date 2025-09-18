package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.AvailabilityRequest;
import ar.sergiovillanueva.chronomed.entity.Account;
import ar.sergiovillanueva.chronomed.entity.Availability;
import ar.sergiovillanueva.chronomed.repository.AvailabilityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void createAvailability(String userId, AvailabilityRequest request) {
        log.debug("Create availability for user {}", userId);
        var availability = objectMapper.convertValue(request, Availability.class);
        var account = new Account();
        account.setUserId(UUID.fromString(userId));
        availability.setAccount(account);
        availabilityRepository.save(availability);
    }

}
