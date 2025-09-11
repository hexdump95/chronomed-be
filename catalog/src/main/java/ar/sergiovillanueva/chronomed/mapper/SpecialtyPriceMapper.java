package ar.sergiovillanueva.chronomed.mapper;

import ar.sergiovillanueva.chronomed.dto.SpecialtyPriceRequest;
import ar.sergiovillanueva.chronomed.dto.SpecialtyPriceResponse;
import ar.sergiovillanueva.chronomed.entity.SpecialtyPrice;

public class SpecialtyPriceMapper {
    public static SpecialtyPrice specialtyPriceRequestToSpecialtyPrice(SpecialtyPriceRequest request, SpecialtyPrice specialtyPrice) {
        specialtyPrice.setPrice(request.getPrice());
        specialtyPrice.setValidFrom(request.getValidFrom());
        specialtyPrice.setValidTo(request.getValidTo());
        return specialtyPrice;
    }

    public static SpecialtyPriceResponse specialtyPriceToSpecialtyPriceResponse(SpecialtyPrice specialtyPrice, SpecialtyPriceResponse response) {
        response.setId(specialtyPrice.getId());
        response.setPrice(specialtyPrice.getPrice());
        response.setValidFrom(specialtyPrice.getValidFrom());
        response.setValidTo(specialtyPrice.getValidTo());
        return response;
    }
}
