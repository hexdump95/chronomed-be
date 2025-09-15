package ar.sergiovillanueva.chronomed.mapper;

import ar.sergiovillanueva.chronomed.dto.InsuranceCoverageRequest;
import ar.sergiovillanueva.chronomed.dto.InsuranceCoverageResponse;
import ar.sergiovillanueva.chronomed.entity.InsuranceCoverage;

public class InsuranceCoverageMapper {
    public static InsuranceCoverage insuranceCoverageRequestToInsuranceCoverage(InsuranceCoverageRequest request, InsuranceCoverage entity) {
        entity.setAmount(request.getAmount());
        entity.setValidFrom(request.getValidFrom());
        entity.setValidTo(request.getValidTo());
        return entity;
    }

    public static InsuranceCoverageResponse insuranceCoverageToInsuranceCoverageResponse(InsuranceCoverage entity, InsuranceCoverageResponse response) {
        response.setId(entity.getId());
        response.setAmount(entity.getAmount());
        response.setValidFrom(entity.getValidFrom());
        response.setValidTo(entity.getValidTo());
        return response;
    }
}
