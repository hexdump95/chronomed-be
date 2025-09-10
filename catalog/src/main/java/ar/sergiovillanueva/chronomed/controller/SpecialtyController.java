package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.dto.PageResponse;
import ar.sergiovillanueva.chronomed.dto.SpecialtyDetailResponse;
import ar.sergiovillanueva.chronomed.dto.SpecialtyRequest;
import ar.sergiovillanueva.chronomed.dto.SpecialtyResponse;
import ar.sergiovillanueva.chronomed.service.NotFoundServiceException;
import ar.sergiovillanueva.chronomed.service.SpecialtyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/specialties")
public class SpecialtyController {
    private final Logger log = LoggerFactory.getLogger(SpecialtyController.class);
    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping
    public PageResponse<SpecialtyResponse> getSpecialties(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page
    ) {
        log.debug("GET request to getSpecialties");
        return specialtyService.findAll(name, page);
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
    public ResponseEntity<SpecialtyResponse> deleteSpecialty(@PathVariable Long id) {
        log.debug("DELETE request to deleteSpecialty with id {}", id);
        try {
            specialtyService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
