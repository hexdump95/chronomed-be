package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.dto.SelectEntityResponse;
import ar.sergiovillanueva.chronomed.service.InsuranceService;
import ar.sergiovillanueva.chronomed.service.InsuranceTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/type/{insuranceTypeId}")
    public List<SelectEntityResponse> getInsurancesByType(@PathVariable Long insuranceTypeId) {
        log.debug("GET request to getInsurancesByType");
        return insuranceService.findAllByInsuranceTypeId(insuranceTypeId);
    }

    @GetMapping("/types/all")
    public List<SelectEntityResponse> getAllInsuranceTypes() {
        log.debug("GET request to getAllInsuranceTypes");
        return insuranceTypeService.findAllInsuranceTypes();
    }

}
