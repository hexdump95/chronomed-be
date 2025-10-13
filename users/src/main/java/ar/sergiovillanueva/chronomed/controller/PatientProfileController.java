package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.service.DomicileService;
import ar.sergiovillanueva.chronomed.service.PatientProfileService;
import ar.sergiovillanueva.chronomed.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
        log.debug("GET request to getPatientProfile");
        try {
            return ResponseEntity.ok(patientProfileService.getProfile(JwtUtils.extractUserId()));
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<Void> updatePatientProfile(@RequestBody PatientRequest request) {
        log.debug("PUT request to updatePatientProfile");
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
        log.debug("GET request to getDomicile");
        try {
            return ResponseEntity.ok(domicileService.getDomicile(JwtUtils.extractUserId()));
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/domicile")
    public ResponseEntity<Void> updateDomicile(@RequestBody DomicileRequest request) {
        log.debug("PUT request to updateDomicile");
        try {
            domicileService.updateDomicile(JwtUtils.extractUserId(), request);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/comorbidities")
    public ResponseEntity<List<Long>> getComorbidities() {
        log.debug("GET request to getComorbidities");
        try {
            return ResponseEntity.ok(patientProfileService.getComorbidities(JwtUtils.extractUserId()));
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/comorbidities")
    public ResponseEntity<Void> updateComorbidities(@RequestBody List<Long> comorbidityIds) {
        log.debug("PUT request to updateComorbidities");
        try {
            patientProfileService.updateComorbidities(JwtUtils.extractUserId(), comorbidityIds);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/insurances")
    public ResponseEntity<List<PatientInsuranceResponse>> getInsurances() {
        log.debug("GET request to getInsurances");
        try {
            return ResponseEntity.ok(patientProfileService.getInsurances(JwtUtils.extractUserId()));
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/insurances")
    public ResponseEntity<Void> createPatientInsurance(@RequestBody PatientInsuranceRequest request) {
        log.debug("POST request to createPatientInsurance");
        try {
            var id = patientProfileService.createInsurance(JwtUtils.extractUserId(), request);
            return ResponseEntity.created(new URI("/api/v1/patient/insurances/" + id)).build();
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/insurances/{id}")
    public ResponseEntity<Void> updatePatientInsurance(@PathVariable Long id, @RequestBody PatientInsuranceRequest request) {
        log.debug("PUT request to updatePatientInsurance");
        try {
            patientProfileService.updateInsurance(id, JwtUtils.extractUserId(), request);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/insurances/{id}")
    public ResponseEntity<Void> deletePatientInsurance(@PathVariable Long id) {
        log.debug("DELETE request to deletePatientInsurance");
        try {
            patientProfileService.deleteInsurance(id, JwtUtils.extractUserId());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/sex")
    public List<SelectEntityResponse> getSex() {
        return patientProfileService.getSex();
    }

    @GetMapping("/self-perceived-identities")
    public List<SelectEntityResponse> getSelfPerceivedIdentities() {
        return patientProfileService.getSelfPerceivedIdentities();
    }

    @GetMapping("/document-types")
    public List<SelectEntityResponse> getDocumentTypes() {
        return patientProfileService.getDocumentTypes();
    }

}
