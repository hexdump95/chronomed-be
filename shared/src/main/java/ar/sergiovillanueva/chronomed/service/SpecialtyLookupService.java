package ar.sergiovillanueva.chronomed.service;

import java.util.List;

public interface SpecialtyLookupService {
    boolean verifyExistingIds(List<Long> ids);
}
