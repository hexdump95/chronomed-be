package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.entity.*;
import ar.sergiovillanueva.chronomed.repository.*;
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
    private final InsuranceLookupService insuranceLookupService;
    private final SexRepository sexRepository;
    private final SelfPerceivedIdentityRepository selfPerceivedIdentityRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final PatientInsuranceRepository patientInsuranceRepository;

    public PatientProfileServiceImpl(PatientRepository patientRepository, PatientAuthService patientAuthService,
                                     PatientSyncService patientSyncService, ComorbidityLookupService comorbidityLookupService,
                                     InsuranceLookupService insuranceLookupService, SexRepository sexRepository,
                                     SelfPerceivedIdentityRepository selfPerceivedIdentityRepository,
                                     DocumentTypeRepository documentTypeRepository, PatientInsuranceRepository patientInsuranceRepository) {
        this.patientRepository = patientRepository;
        this.patientAuthService = patientAuthService;
        this.patientSyncService = patientSyncService;
        this.comorbidityLookupService = comorbidityLookupService;
        this.insuranceLookupService = insuranceLookupService;
        this.sexRepository = sexRepository;
        this.selfPerceivedIdentityRepository = selfPerceivedIdentityRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.patientInsuranceRepository = patientInsuranceRepository;
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
        if (patient.getSex() != null) {
            response.setSexId(patient.getSex().getId());
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
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setSex(sex);
        patientRepository.save(patient);
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

    @Override
    @Transactional(readOnly = true)
    public List<PatientInsuranceResponse> getInsurances(String patientId) {
        log.debug("get insurances for patient {}", patientId);
        var patient = patientRepository.findById(UUID.fromString(patientId))
                .orElseThrow(() -> new NotFoundServiceException("patient not found"));
        return patient.getPatientInsurances().stream().map(i -> {
            var response = new PatientInsuranceResponse();
            response.setId(i.getId());
            response.setAffiliateNumber(i.getAffiliateNumber());
            response.setInsuranceId(i.getInsuranceId());
            return response;
        }).toList();
    }

    @Override
    @Transactional
    public Long createInsurance(String patientId, PatientInsuranceRequest request) {
        log.debug("create insurance for patient {}", patientId);

        var patient = patientRepository.findById(UUID.fromString(patientId))
                .orElseThrow(() -> new RuntimeException("patient not found"));

        var patientInsurance = new PatientInsurance();
        patientInsurance.setInsuranceId(request.getInsuranceId());
        patientInsurance.setAffiliateNumber(request.getAffiliateNumber());
        patientInsurance.setPatient(patient);

        var existingInsurances = insuranceLookupService.verifyExistingIds(List.of(request.getInsuranceId()));
        if (!existingInsurances) {
            log.debug("insurance lookup failed");
            throw new RuntimeException("error with insurance lookup");
        }
        patientInsuranceRepository.save(patientInsurance);
        return patientInsurance.getId();
    }

    @Override
    @Transactional
    public void updateInsurance(Long id, String patientId, PatientInsuranceRequest request) {
        log.debug("update insurance for patient {}", patientId);
        var patientInsurance = patientInsuranceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("patient not found"));

        if (!patientInsurance.getPatient().getId().equals(UUID.fromString(patientId))) {
            throw new RuntimeException("insurance isn't from this user");
        }

        patientInsurance.setInsuranceId(request.getInsuranceId());
        patientInsurance.setAffiliateNumber(request.getAffiliateNumber());

        var existingInsurances = insuranceLookupService.verifyExistingIds(List.of(request.getInsuranceId()));
        if (!existingInsurances) {
            log.debug("insurance lookup failed");
            throw new RuntimeException("error with insurance lookup");
        }
        patientInsuranceRepository.save(patientInsurance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SelectEntityResponse> getSex() {
        return sexRepository.findAll().stream().map(x -> {
            var response = new SelectEntityResponse();
            response.setId(x.getId());
            response.setName(x.getName());
            return response;
        }).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SelectEntityResponse> getSelfPerceivedIdentities() {
        return selfPerceivedIdentityRepository.findAll().stream().map(x -> {
            var response = new SelectEntityResponse();
            response.setId(x.getId());
            response.setName(x.getName());
            return response;
        }).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SelectEntityResponse> getDocumentTypes() {
        return documentTypeRepository.findAll().stream().map(x -> {
            var response = new SelectEntityResponse();
            response.setId(x.getId());
            response.setName(x.getName());
            return response;
        }).toList();
    }
}
