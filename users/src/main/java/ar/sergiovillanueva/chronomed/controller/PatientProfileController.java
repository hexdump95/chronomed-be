package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.service.DomicileService;
import ar.sergiovillanueva.chronomed.service.PatientProfileService;
import ar.sergiovillanueva.chronomed.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientProfileController {
    private final Logger log = LoggerFactory.getLogger(PatientProfileController.class);
    private final PatientProfileService patientProfileService;
    private final DomicileService domicileService;

    public PatientProfileController(PatientProfileService patientProfileService, DomicileService domicileService) {
        this.patientProfileService = patientProfileService;
        this.domicileService = domicileService;
    }

    @GetMapping
    public ResponseEntity<PatientDetailResponse> getPatientProfile() {
        try {
            return ResponseEntity.ok(patientProfileService.getProfile(JwtUtils.extractUserId()));
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<Void> updatePatientProfile(@RequestBody PatientRequest request) {
        try {
            patientProfileService.updateProfile(JwtUtils.extractUserId(), request);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/domicile")
    public ResponseEntity<DomicileResponse> getDomicile() {
        try {
            return ResponseEntity.ok(domicileService.getDomicile(JwtUtils.extractUserId()));
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/domicile")
    public ResponseEntity<Void> updateDomicile(@RequestBody DomicileRequest request) {
        try {
            domicileService.updateDomicile(JwtUtils.extractUserId(), request);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
