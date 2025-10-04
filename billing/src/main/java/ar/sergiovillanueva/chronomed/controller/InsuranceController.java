package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.service.InsuranceCoverageService;
import ar.sergiovillanueva.chronomed.service.InsuranceService;
import ar.sergiovillanueva.chronomed.service.InsuranceTypeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/insurances")
public class InsuranceController {
    private final Logger log = LoggerFactory.getLogger(InsuranceController.class);
    private final InsuranceService insuranceService;
    private final InsuranceTypeService insuranceTypeService;
    private final InsuranceCoverageService insuranceCoverageService;

    public InsuranceController(InsuranceService insuranceService, InsuranceTypeService insuranceTypeService, InsuranceCoverageService insuranceCoverageService) {
        this.insuranceService = insuranceService;
        this.insuranceTypeService = insuranceTypeService;
        this.insuranceCoverageService = insuranceCoverageService;
    }

    @GetMapping
    public PageResponse<InsuranceResponse> getInsurances(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page
    ) {
        log.debug("GET request to getInsurances");
        return insuranceService.findInsurances(search, page);
    }

    @GetMapping("/all")
    public List<SelectEntityResponse> getInsurancesByType(@RequestParam Long insuranceTypeId) {
        log.debug("GET request to getInsurancesByType");
        return insuranceService.findAllByInsuranceTypeId(insuranceTypeId);
    }

    @GetMapping("/byIds")
    public ResponseEntity<List<SelectEntityResponse>> getInsurancesByIds(@RequestParam List<Long> insuranceIds) {
        log.debug("GET request to getInsurancesByIds");
        try {
            return ResponseEntity.ok(insuranceService.findInsurancesByIds(insuranceIds));
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceDetailResponse> getOne(@PathVariable Long id) {
        log.debug("GET request to getInsurance by id");
        try {
            return ResponseEntity.ok(insuranceService.getOne(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<InsuranceResponse> createInsurance(@RequestBody InsuranceRequest request) {
        log.debug("POST request to createInsurance");
        var insurance = insuranceService.save(request);
        return ResponseEntity.created(URI.create("/api/v1/insurances/" + insurance.getId())).body(insurance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsuranceResponse> updateInsurance(@PathVariable Long id, @RequestBody InsuranceRequest request) {
        log.debug("PUT request to updateInsurance");
        try {
            return ResponseEntity.ok(insuranceService.update(id, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable Long id) {
        log.debug("DELETE request to deleteInsurance with id {}", id);
        try {
            insuranceService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{insuranceId}/coverages")
    public ResponseEntity<InsuranceCoverageResponse> createInsuranceCoverage(
            @PathVariable Long insuranceId,
            @Valid @RequestBody InsuranceCoverageRequest request
    ) {
        log.debug("POST request to createInsuranceCoverage for insurance {}", insuranceId);
        var coverage = insuranceCoverageService.save(insuranceId, request);
        return ResponseEntity.created(URI.create("/api/v1/insurances/coverages/" + insuranceId)).body(coverage);
    }

    @GetMapping("/coverages/{id}")
    public ResponseEntity<InsuranceCoverageResponse> getInsuranceCoverage(@PathVariable Long id) {
        log.debug("GET request to getInsuranceCoverage id {}", id);
        try {
            return ResponseEntity.ok(insuranceCoverageService.getOne(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/types/all")
    public List<SelectEntityResponse> getAllInsuranceTypes() {
        log.debug("GET request to getAllInsuranceTypes");
        return insuranceTypeService.findAllInsuranceTypes();
    }
}
