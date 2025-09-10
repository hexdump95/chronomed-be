package ar.sergiovillanueva.chronomed.mapper;

import ar.sergiovillanueva.chronomed.dto.SpecialtyDetailResponse;
import ar.sergiovillanueva.chronomed.dto.SpecialtyPriceResponse;
import ar.sergiovillanueva.chronomed.dto.SpecialtyRequest;
import ar.sergiovillanueva.chronomed.dto.SpecialtyResponse;
import ar.sergiovillanueva.chronomed.entity.Specialty;

import java.util.List;

public class SpecialtyMapper {
    public static SpecialtyResponse specialtyToSpecialtyResponse(Specialty specialty, SpecialtyResponse response) {
        response.setId(specialty.getId());
        response.setName(specialty.getName());
        response.setDescription(specialty.getDescription());
        return response;
    }

    public static Specialty specialtyRequestToSpecialty(SpecialtyRequest request, Specialty specialty) {
        specialty.setName(request.getName());
        specialty.setDescription(request.getDescription());
        return specialty;
    }

    public static SpecialtyDetailResponse specialtyToSpecialtyDetailResponse(Specialty specialty, SpecialtyDetailResponse response) {
        response.setId(specialty.getId());
        response.setName(specialty.getName());
        response.setDescription(specialty.getDescription());
        var specialtyPrices = specialty.getSpecialtyPrices().stream().map(sp -> {
            var r = new SpecialtyPriceResponse();
            r.setId(sp.getId());
            r.setValidFrom(sp.getValidFrom());
            r.setValidTo(sp.getValidTo());
            r.setPrice(sp.getPrice());
            return r;
        }).toList();
        response.setSpecialtyPrices(specialtyPrices);
        return response;
    }
}
