package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.DomicileRequest;
import ar.sergiovillanueva.chronomed.dto.DomicileResponse;

public interface DomicileService {
    DomicileResponse getDomicile(String patientId);
    void updateDomicile(String patientId, DomicileRequest request);
}
