package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.dto.PageResponse;
import ar.sergiovillanueva.chronomed.dto.SpecialtyRequest;
import ar.sergiovillanueva.chronomed.dto.SpecialtyResponse;
import ar.sergiovillanueva.chronomed.service.SpecialtyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public SpecialtyResponse createSpecialty(@RequestBody SpecialtyRequest specialty) {
        log.debug("POST request to createSpecialty");
        return specialtyService.save(specialty);
    }

}
