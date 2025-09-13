package ar.sergiovillanueva.chronomed.mapper;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.entity.Specialty;
import ar.sergiovillanueva.chronomed.entity.SpecialtyPrice;

import java.util.Comparator;

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

    public static SelectEntityResponse specialtyToSelectEntityResponse(Specialty entity, SelectEntityResponse response) {
        response.setId(entity.getId());
        response.setName(entity.getName());
        return response;
    }

    public static SpecialtyDetailResponse specialtyToSpecialtyDetailResponse(Specialty specialty, SpecialtyDetailResponse response) {
        response.setId(specialty.getId());
        response.setName(specialty.getName());
        response.setDescription(specialty.getDescription());
        var specialtyPrices = specialty.getSpecialtyPrices().stream()
                .sorted(Comparator.comparing(SpecialtyPrice::getValidFrom).reversed())
                .map(sp -> {
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
