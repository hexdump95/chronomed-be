package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.config.AuthRole;
import ar.sergiovillanueva.chronomed.dto.AvailabilityDetailResponse;
import ar.sergiovillanueva.chronomed.dto.AvailabilityRequest;
import ar.sergiovillanueva.chronomed.dto.AvailabilityResponse;
import ar.sergiovillanueva.chronomed.security.RolesRequired;
import ar.sergiovillanueva.chronomed.service.AvailabilityService;
import ar.sergiovillanueva.chronomed.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/availabilities")
public class AvailabilityController {
    private final Logger log = LoggerFactory.getLogger(AvailabilityController.class);
    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @RolesRequired({AuthRole.ADMIN, AuthRole.DOCTOR})
    @GetMapping
    public ResponseEntity<List<AvailabilityResponse>> getAvailabilities() {
        log.debug("GET request to getAvailabilities");
        try {
            return ResponseEntity.ok(availabilityService.getAvailabilitiesByUserId(JwtUtils.extractUserId()));
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @RolesRequired({AuthRole.ADMIN, AuthRole.DOCTOR})
    @GetMapping("/{id}")
    public ResponseEntity<AvailabilityDetailResponse> getAvailability(@PathVariable Long id) {
        log.debug("GET request to getAvailability by id: {}", id);
        try {
            return ResponseEntity.ok(availabilityService.getAvailabilityByIdAndUserId(id, JwtUtils.extractUserId()));
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @RolesRequired({AuthRole.ADMIN, AuthRole.DOCTOR})
    @PostMapping
    public ResponseEntity<Void> createAvailability(@RequestBody AvailabilityRequest request) {
        log.debug("POST request to createAvailability {}", request);
        try {
            availabilityService.createAvailability(JwtUtils.extractUserId(), request);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
