package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.dto.PageResponse;
import ar.sergiovillanueva.chronomed.dto.ComorbidityRequest;
import ar.sergiovillanueva.chronomed.dto.ComorbidityResponse;
import ar.sergiovillanueva.chronomed.dto.SelectEntityResponse;
import ar.sergiovillanueva.chronomed.service.NotFoundServiceException;
import ar.sergiovillanueva.chronomed.service.ComorbidityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comorbidities")
public class ComorbidityController {
    private final Logger log = LoggerFactory.getLogger(ComorbidityController.class);
    private final ComorbidityService comorbidityService;

    public ComorbidityController(ComorbidityService comorbidityService) {
        this.comorbidityService = comorbidityService;
    }

    @GetMapping
    public PageResponse<ComorbidityResponse> getComorbidities(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page
    ) {
        log.debug("GET request to getComorbidities");
        return comorbidityService.findComorbidities(name, page);
    }

    @GetMapping("/all")
    public List<SelectEntityResponse> getAllComorbidities() {
        log.debug("GET request to getAllComorbidities");
        return comorbidityService.findAllComorbidities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComorbidityResponse> getComorbidity(@PathVariable Long id) {
        log.debug("GET request to get comorbidity with id {}", id);
        try {
            return ResponseEntity.ok(comorbidityService.findOne(id));
        } catch (NotFoundServiceException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ComorbidityResponse> createComorbidity(@RequestBody ComorbidityRequest request) {
        log.debug("POST request to createComorbidity");
        var comorbidity = comorbidityService.save(request);
        return ResponseEntity.created(URI.create("/api/v1/comorbidities/" + comorbidity.getId())).body(comorbidity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComorbidityResponse> updateComorbidity(@PathVariable Long id, @RequestBody ComorbidityRequest request) {
        log.debug("PUT request to updateComorbidity");
        try {
            return ResponseEntity.ok(comorbidityService.update(id, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComorbidity(@PathVariable Long id) {
        log.debug("DELETE request to deleteComorbidity with id {}", id);
        try {
            comorbidityService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
