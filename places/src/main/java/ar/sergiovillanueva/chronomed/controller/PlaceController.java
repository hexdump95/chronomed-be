package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.dto.LocalityResponse;
import ar.sergiovillanueva.chronomed.dto.SelectEntityResponse;
import ar.sergiovillanueva.chronomed.service.DistrictService;
import ar.sergiovillanueva.chronomed.service.LocalityService;
import ar.sergiovillanueva.chronomed.service.NotFoundServiceException;
import ar.sergiovillanueva.chronomed.service.ProvinceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/places")
public class PlaceController {

    private final Logger log = LoggerFactory.getLogger(PlaceController.class);
    private final ProvinceService provinceService;
    private final DistrictService districtService;
    private final LocalityService localityService;

    public PlaceController(ProvinceService provinceService, DistrictService districtService, LocalityService localityService) {
        this.provinceService = provinceService;
        this.districtService = districtService;
        this.localityService = localityService;
    }

    @GetMapping("/provinces/all")
    public List<SelectEntityResponse> getAllProvinces() {
        log.debug("GET request to getAllProvinces");
        return provinceService.findAll();
    }

    @GetMapping("/provinces/{provinceId}/districts/all")
    public List<SelectEntityResponse> getAllDistrictsByProvinceId(@PathVariable Long provinceId) {
        log.debug("GET request to getAllDistrictsByProvinceId");
        return districtService.findAllByProvinceId(provinceId);
    }

    @GetMapping("/provinces/districts/{districtId}/localities/all")
    public List<SelectEntityResponse> getAllLocalitiesByDistrictId(@PathVariable Long districtId) {
        log.debug("GET request to getAllLocalitiesByDistrictId");
        return localityService.findAllByDistrictId(districtId);
    }

    @GetMapping("/provinces/districts/localities/{localityId}")
    public ResponseEntity<LocalityResponse> getLocality(@PathVariable Long localityId) {
        log.debug("GET request to getLocality");
        try {
            return ResponseEntity.ok(localityService.getLocality(localityId));
        } catch (NotFoundServiceException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
