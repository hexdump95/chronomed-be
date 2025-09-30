package ar.sergiovillanueva.chronomed.mapper;

import ar.sergiovillanueva.chronomed.dto.InsuranceCoverageResponse;
import ar.sergiovillanueva.chronomed.dto.InsuranceDetailResponse;
import ar.sergiovillanueva.chronomed.dto.InsuranceRequest;
import ar.sergiovillanueva.chronomed.dto.InsuranceResponse;
import ar.sergiovillanueva.chronomed.entity.Insurance;
import ar.sergiovillanueva.chronomed.entity.InsuranceCoverage;
import ar.sergiovillanueva.chronomed.entity.InsuranceType;

import java.util.Comparator;

public class InsuranceMapper {
    public static InsuranceResponse insuranceToInsuranceResponse(Insurance entity, InsuranceResponse response) {
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setInsuranceTypeName(entity.getInsuranceType().getName());
        return response;
    }

    public static InsuranceDetailResponse insuranceToInsuranceDetailResponse(Insurance entity, InsuranceDetailResponse response) {
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setInsuranceTypeName(entity.getInsuranceType().getName());
        var coverages = entity.getCoverages().stream()
                .sorted(Comparator.comparing(InsuranceCoverage::getValidFrom).reversed())
                .map(c -> {
                    var res = new InsuranceCoverageResponse();
                    return InsuranceCoverageMapper.insuranceCoverageToInsuranceCoverageResponse(c, res);
                }).toList();
        response.setCoverages(coverages);
        return response;
    }

    public static Insurance insuranceRequestToInsurance(InsuranceRequest request, Insurance entity) {
        var insuranceType = new InsuranceType();
        insuranceType.setId(request.getInsuranceTypeId());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setInsuranceType(insuranceType);
        return entity;
    }
}
