package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.PatientDetailResponse;
import ar.sergiovillanueva.chronomed.dto.PatientRequest;
import ar.sergiovillanueva.chronomed.entity.DocumentType;
import ar.sergiovillanueva.chronomed.entity.SelfPerceivedIdentity;
import ar.sergiovillanueva.chronomed.entity.Sex;
import ar.sergiovillanueva.chronomed.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PatientProfileServiceImpl implements PatientProfileService {
    private final Logger log = LoggerFactory.getLogger(PatientProfileServiceImpl.class);
    private final PatientRepository patientRepository;
    private final PatientAuthService patientAuthService;
    private final PatientSyncService patientSyncService;

    public PatientProfileServiceImpl(PatientRepository patientRepository, PatientAuthService patientAuthService, PatientSyncService patientSyncService) {
        this.patientRepository = patientRepository;
        this.patientAuthService = patientAuthService;
        this.patientSyncService = patientSyncService;
    }

    @Override
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
}
