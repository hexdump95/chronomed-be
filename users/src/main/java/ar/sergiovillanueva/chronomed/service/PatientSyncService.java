package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.KeycloakUser;
import ar.sergiovillanueva.chronomed.entity.Patient;
import ar.sergiovillanueva.chronomed.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientSyncService {
    private final PatientRepository repository;

    public PatientSyncService(PatientRepository repository) {
        this.repository = repository;
    }

    public Patient synchronizeBEAccount(KeycloakUser kcUser) {
        var entity = new Patient();
        entity.setId(kcUser.getId());
        repository.save(entity);
        return entity;
    }
}
