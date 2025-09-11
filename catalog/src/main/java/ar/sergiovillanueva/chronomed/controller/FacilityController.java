package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.service.NotFoundServiceException;
import ar.sergiovillanueva.chronomed.service.FacilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/facilities")
public class FacilityController {
    private final Logger log = LoggerFactory.getLogger(FacilityController.class);
    private final FacilityService facilityService;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping
    public PageResponse<FacilityResponse> getFacilities(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page
    ) {
        log.debug("GET request to getFacilities");
        return facilityService.findAll(name, page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacilityDetailResponse> getFacility(@PathVariable Long id) {
        log.debug("GET request to get facility with id {}", id);
        try {
            return ResponseEntity.ok(facilityService.findOne(id));
        } catch (NotFoundServiceException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FacilityResponse> createFacility(@RequestBody FacilityRequest request) {
        log.debug("POST request to createFacility");
        var facility = facilityService.save(request);
        return ResponseEntity.created(URI.create("/api/v1/facilities/" + facility.getId())).body(facility);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacilityResponse> updateFacility(@PathVariable Long id, @RequestBody FacilityRequest request) {
        log.debug("PUT request to updateFacility");
        try {
            return ResponseEntity.ok(facilityService.update(id, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FacilityResponse> deleteFacility(@PathVariable Long id) {
        log.debug("DELETE request to deleteFacility with id {}", id);
        try {
            facilityService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
