package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.service.NotFoundServiceException;
import ar.sergiovillanueva.chronomed.service.SpecialtyPriceService;
import ar.sergiovillanueva.chronomed.service.SpecialtyService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/specialties")
public class SpecialtyController {
    private final Logger log = LoggerFactory.getLogger(SpecialtyController.class);
    private final SpecialtyService specialtyService;
    private final SpecialtyPriceService specialtyPriceService;

    public SpecialtyController(SpecialtyService specialtyService, SpecialtyPriceService specialtyPriceService) {
        this.specialtyService = specialtyService;
        this.specialtyPriceService = specialtyPriceService;
    }

    @GetMapping
    public PageResponse<SpecialtyResponse> getSpecialties(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page
    ) {
        log.debug("GET request to getSpecialties");
        return specialtyService.findSpecialties(search, page);
    }

    @GetMapping("/all")
    public List<SelectEntityResponse> getAllSpecialties() {
        log.debug("GET request to getAllSpecialties");
        return specialtyService.findAllSpecialties();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDetailResponse> getSpecialty(@PathVariable Long id) {
        log.debug("GET request to get specialty with id {}", id);
        try {
            return ResponseEntity.ok(specialtyService.findOne(id));
        } catch (NotFoundServiceException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<SpecialtyResponse> createSpecialty(@RequestBody SpecialtyRequest request) {
        log.debug("POST request to createSpecialty");
        var specialty = specialtyService.save(request);
        return ResponseEntity.created(URI.create("/api/v1/specialties/" + specialty.getId())).body(specialty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialtyResponse> updateSpecialty(@PathVariable Long id, @RequestBody SpecialtyRequest request) {
        log.debug("PUT request to updateSpecialty");
        try {
            return ResponseEntity.ok(specialtyService.update(id, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable Long id) {
        log.debug("DELETE request to deleteSpecialty with id {}", id);
        try {
            specialtyService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{specialtyId}/prices")
    public ResponseEntity<SpecialtyPriceResponse> createSpecialtyPrice(
            @PathVariable Long specialtyId,
            @Valid @RequestBody SpecialtyPriceRequest request
    ) {
        log.debug("POST request to createSpecialtyPrice for specialty {}", specialtyId);
        try {
            var specialty = specialtyPriceService.save(specialtyId, request);
            return ResponseEntity.created(URI.create("/api/v1/specialties/prices/" + specialtyId)).body(specialty);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/prices/{id}")
    public ResponseEntity<SpecialtyPriceResponse> getSpecialtyPrice(@PathVariable Long id) {
        log.debug("GET request to getSpecialtyPrice for specialty {}", id);
        try {
            return ResponseEntity.ok(specialtyPriceService.getSpecialtyPrice(id));
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
