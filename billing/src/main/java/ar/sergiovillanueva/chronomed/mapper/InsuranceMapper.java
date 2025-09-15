package ar.sergiovillanueva.chronomed.mapper;

import ar.sergiovillanueva.chronomed.dto.InsuranceCoverageResponse;
import ar.sergiovillanueva.chronomed.dto.InsuranceDetailResponse;
import ar.sergiovillanueva.chronomed.dto.InsuranceResponse;
import ar.sergiovillanueva.chronomed.entity.Insurance;

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
        var coverages = entity.getCoverages().stream().map(c -> {
            var res = new InsuranceCoverageResponse();
            res.setId(c.getId());
            res.setAmount(c.getAmount());
            res.setValidFrom(c.getValidFrom());
            res.setValidTo(c.getValidTo());
            return res;
        }).toList();
        response.setCoverages(coverages);
        return response;
    }
}
