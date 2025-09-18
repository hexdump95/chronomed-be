package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.config.AuthRole;
import ar.sergiovillanueva.chronomed.dto.AvailabilityRequest;
import ar.sergiovillanueva.chronomed.dto.ProfileRequest;
import ar.sergiovillanueva.chronomed.dto.ProfileResponse;
import ar.sergiovillanueva.chronomed.security.RolesRequired;
import ar.sergiovillanueva.chronomed.service.AvailabilityService;
import ar.sergiovillanueva.chronomed.service.StaffProfileService;
import ar.sergiovillanueva.chronomed.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
public class StaffProfileController {
    private final Logger log = LoggerFactory.getLogger(StaffProfileController.class);
    private final StaffProfileService profileService;
    private final AvailabilityService availabilityService;

    public StaffProfileController(StaffProfileService profileService, AvailabilityService availabilityService) {
        this.profileService = profileService;
        this.availabilityService = availabilityService;
    }

    @RolesRequired({AuthRole.ADMIN, AuthRole.DOCTOR, AuthRole.RECEPTIONIST})
    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile() {
        log.debug("GET request to getProfile");
        return ResponseEntity.ok(profileService.getProfile(JwtUtils.extractUserId()));
    }

    @RolesRequired({AuthRole.ADMIN, AuthRole.DOCTOR, AuthRole.RECEPTIONIST})
    @PutMapping
    public ResponseEntity<Void> updateProfile(@RequestBody ProfileRequest request) {
        log.debug("PUT request to update profile {}", request);
        try {
            profileService.updateProfile(JwtUtils.extractUserId(), request);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RolesRequired({AuthRole.ADMIN, AuthRole.DOCTOR})
    @GetMapping("/insurances")
    public ResponseEntity<List<Long>> getInsurances() {
        log.debug("GET request to getInsurances");
        try {
            return ResponseEntity.ok(profileService.getInsuranceIds(JwtUtils.extractUserId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RolesRequired({AuthRole.ADMIN, AuthRole.DOCTOR})
    @PutMapping("/insurances")
    public ResponseEntity<Void> updateInsurances(@RequestBody List<Long> insuranceIds) {
        log.debug("PUT request to update insurances {}", insuranceIds);
        try {
            profileService.updateInsurances(JwtUtils.extractUserId(), insuranceIds);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RolesRequired({AuthRole.ADMIN, AuthRole.DOCTOR})
    @PostMapping("/availabilities")
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
