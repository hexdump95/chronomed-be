package ar.sergiovillanueva.chronomed.mapper;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.entity.Comorbidity;

import java.util.Comparator;

public class ComorbidityMapper {
    public static ComorbidityResponse comorbidityToComorbidityResponse(Comorbidity comorbidity, ComorbidityResponse response) {
        response.setId(comorbidity.getId());
        response.setName(comorbidity.getName());
        response.setDescription(comorbidity.getDescription());
        return response;
    }

    public static Comorbidity comorbidityRequestToComorbidity(ComorbidityRequest request, Comorbidity comorbidity) {
        comorbidity.setName(request.getName());
        comorbidity.setDescription(request.getDescription());
        return comorbidity;
    }

}
