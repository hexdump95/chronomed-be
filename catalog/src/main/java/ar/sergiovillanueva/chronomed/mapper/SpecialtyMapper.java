package ar.sergiovillanueva.chronomed.mapper;

import ar.sergiovillanueva.chronomed.dto.SpecialtyRequest;
import ar.sergiovillanueva.chronomed.dto.SpecialtyResponse;
import ar.sergiovillanueva.chronomed.entity.Specialty;

public class SpecialtyMapper {
    public static SpecialtyResponse toDto(Specialty specialty) {
        if (specialty == null) return null;

        var response = new SpecialtyResponse();
        response.setId(specialty.getId());
        response.setName(specialty.getName());
        return response;
    }

    public static Specialty toEntity(SpecialtyRequest request) {
        if (request == null) return null;

        var specialty = new Specialty();
        specialty.setName(request.getName());
        return specialty;
    }
}
