package ar.sergiovillanueva.chronomed.service;

import java.util.List;

public interface FacilityLookupService {
    boolean verifyExistingIds(List<Long> ids);
}
