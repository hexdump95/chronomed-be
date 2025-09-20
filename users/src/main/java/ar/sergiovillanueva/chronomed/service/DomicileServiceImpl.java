package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.DomicileRequest;
import ar.sergiovillanueva.chronomed.dto.DomicileResponse;
import ar.sergiovillanueva.chronomed.repository.DomicileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DomicileServiceImpl implements DomicileService {
    private final Logger log = LoggerFactory.getLogger(DomicileServiceImpl.class);
    private final DomicileRepository domicileRepository;
    private final LocalityLookupService localityLookupService;

    public DomicileServiceImpl(DomicileRepository domicileRepository, LocalityLookupService localityLookupService) {
        this.domicileRepository = domicileRepository;
        this.localityLookupService = localityLookupService;
    }

    @Override
    public DomicileResponse getDomicile(String patientId) {
        log.debug("get domicile for patientId {}", patientId);
        var domicile = domicileRepository.findByPatientId(UUID.fromString(patientId))
                .orElseThrow(() -> new NotFoundServiceException("domicile not found"));
        var response = new DomicileResponse();
        response.setStreet(domicile.getStreet());
        response.setNumber(response.getNumber());
        response.setFloor(domicile.getFloor());
        response.setLocalityId(domicile.getLocalityId());
        return response;
    }

    @Override
    public void updateDomicile(String patientId, DomicileRequest request) {
        log.debug("update domicile for patientId {}", patientId);
        var domicile = domicileRepository.findByPatientId(UUID.fromString(patientId))
                .orElseThrow(() -> new NotFoundServiceException("domicile not found"));
        domicile.setStreet(request.getStreet());
        domicile.setNumber(request.getNumber());
        domicile.setFloor(request.getFloor());
        var exists = localityLookupService.verifyExistingId(request.getLocalityId());
        if (!exists) {
            throw new RuntimeException("locality not found");
        }
        domicile.setLocalityId(request.getLocalityId());
        domicileRepository.save(domicile);
    }
}
