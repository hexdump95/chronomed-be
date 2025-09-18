package ar.sergiovillanueva.chronomed.service;

import java.util.List;

public interface InsuranceLookupService {
    boolean verifyExistingIds(List<Long> ids);
}
