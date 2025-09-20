package ar.sergiovillanueva.chronomed.service;

import java.util.List;

public interface ComorbidityLookupService {
    boolean verifyExistingIds(List<Long> ids);
}
