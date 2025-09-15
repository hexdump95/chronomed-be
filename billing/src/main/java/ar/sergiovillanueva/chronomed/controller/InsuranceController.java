package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.dto.InsuranceDetailResponse;
import ar.sergiovillanueva.chronomed.dto.InsuranceResponse;
import ar.sergiovillanueva.chronomed.dto.PageResponse;
import ar.sergiovillanueva.chronomed.dto.SelectEntityResponse;
import ar.sergiovillanueva.chronomed.service.InsuranceService;
import ar.sergiovillanueva.chronomed.service.InsuranceTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/insurances")
public class InsuranceController {
    private final Logger log = LoggerFactory.getLogger(InsuranceController.class);
    private final InsuranceService insuranceService;
    private final InsuranceTypeService insuranceTypeService;

    public InsuranceController(InsuranceService insuranceService, InsuranceTypeService insuranceTypeService) {
        this.insuranceService = insuranceService;
        this.insuranceTypeService = insuranceTypeService;
    }

    @GetMapping
    public PageResponse<InsuranceResponse> getInsurances(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page
    ) {
        log.debug("GET request to getInsurances");
        return insuranceService.findInsurances(name, page);
    }

    @GetMapping("/all")
    public List<SelectEntityResponse> getInsurancesByType(@RequestParam Long insuranceTypeId) {
        log.debug("GET request to getInsurancesByType");
        return insuranceService.findAllByInsuranceTypeId(insuranceTypeId);
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

    @GetMapping("/types/all")
    public List<SelectEntityResponse> getAllInsuranceTypes() {
        log.debug("GET request to getAllInsuranceTypes");
        return insuranceTypeService.findAllInsuranceTypes();
    }

}
