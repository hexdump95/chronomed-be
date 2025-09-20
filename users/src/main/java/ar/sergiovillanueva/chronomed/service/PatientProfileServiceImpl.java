package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.PatientDetailResponse;
import ar.sergiovillanueva.chronomed.dto.PatientRequest;
import ar.sergiovillanueva.chronomed.entity.DocumentType;
import ar.sergiovillanueva.chronomed.entity.PatientComorbidity;
import ar.sergiovillanueva.chronomed.entity.SelfPerceivedIdentity;
import ar.sergiovillanueva.chronomed.entity.Sex;
import ar.sergiovillanueva.chronomed.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PatientProfileServiceImpl implements PatientProfileService {
    private final Logger log = LoggerFactory.getLogger(PatientProfileServiceImpl.class);
    private final PatientRepository patientRepository;
    private final PatientAuthService patientAuthService;
    private final PatientSyncService patientSyncService;
    private final ComorbidityLookupService comorbidityLookupService;

    public PatientProfileServiceImpl(PatientRepository patientRepository, PatientAuthService patientAuthService, PatientSyncService patientSyncService, ComorbidityLookupService comorbidityLookupService) {
        this.patientRepository = patientRepository;
        this.patientAuthService = patientAuthService;
        this.patientSyncService = patientSyncService;
        this.comorbidityLookupService = comorbidityLookupService;
    }

    @Override
    @Transactional(readOnly = true)
    public PatientDetailResponse getProfile(String userId) {
        log.debug("get profile for user {}", userId);
        var kcUser = patientAuthService.getUserById(UUID.fromString(userId));
        var patient = patientRepository.findById(UUID.fromString(userId))
                .orElseGet(() -> patientSyncService.synchronizeBEAccount(kcUser));
        var response = new PatientDetailResponse();
        response.setUsername(kcUser.getUsername());
        response.setFirstName(kcUser.getFirstName());
        response.setLastName(kcUser.getLastName());
        response.setDateOfBirth(patient.getDateOfBirth());
        response.setIdentityDocument(patient.getIdentityDocument());
        if (patient.getDocumentType() != null) {
            response.setDocumentTypeId(patient.getDocumentType().getId());
        }
        if (patient.getSelfPerceivedIdentity() != null) {
            response.setSelfPerceivedIdentityId(patient.getSelfPerceivedIdentity().getId());
        }
        return response;
    }

    @Override
    @Transactional
    public void updateProfile(String userId, PatientRequest request) {
        log.debug("update profile for user {}", userId);
        var patient = patientRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new NotFoundServiceException("patient not found"));
        var documentType = new DocumentType();
        documentType.setId(request.getDocumentTypeId());
        var selfPerceivedIdentity = new SelfPerceivedIdentity();
        selfPerceivedIdentity.setId(request.getSelfPerceivedIdentityId());
        var sex = new Sex();
        sex.setId(request.getSexId());

        patient.setIdentityDocument(request.getIdentityDocument());
        patient.setDocumentType(documentType);
        patient.setSelfPerceivedIdentity(selfPerceivedIdentity);
        patient.setSex(sex);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getComorbidities(String patientId) {
        var patient = patientRepository.findById(UUID.fromString(patientId))
                .orElseThrow(() -> new NotFoundServiceException("patient not found"));
        return patient.getPatientComorbidities().stream()
                .map(PatientComorbidity::getComorbidityId).toList();
    }

    @Override
    @Transactional
    public void updateComorbidities(String patientId, List<Long> comorbidityIds) {
        var patient = patientRepository.findById(UUID.fromString(patientId))
                .orElseThrow(() -> new NotFoundServiceException("patient not found"));

        var existingComorbidities = comorbidityLookupService.verifyExistingIds(comorbidityIds);
        if (!existingComorbidities) {
            log.debug("comorbidity lookup failed");
            throw new RuntimeException("error with comorbidity lookup");
        }

        var comorbidities = comorbidityIds.stream().map(x -> {
            var comorbidity = new PatientComorbidity();
            comorbidity.setComorbidityId(x);
            return comorbidity;
        }).toList();
        patient.getPatientComorbidities().clear();
        patient.getPatientComorbidities().addAll(comorbidities);
        patientRepository.save(patient);
    }
}
