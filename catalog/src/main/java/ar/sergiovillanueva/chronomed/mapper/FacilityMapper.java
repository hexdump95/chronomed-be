package ar.sergiovillanueva.chronomed.mapper;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.entity.Facility;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

public class FacilityMapper {
    public static FacilityResponse facilityToFacilityResponse(Facility entity, FacilityResponse response) {
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setTaxIdentificationNumber(entity.getTaxIdentificationNumber());
        response.setEmail(entity.getEmail());
        response.setPhone(entity.getPhone());
        return response;
    }

    public static Facility facilityRequestToFacility(FacilityRequest request, Facility entity) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setTaxIdentificationNumber(request.getTaxIdentificationNumber());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());
        GeometryFactory gf = new GeometryFactory();
        Point point = gf.createPoint(new Coordinate(request.getCoordinates().getX(), request.getCoordinates().getY()));
        entity.setCoordinates(point);
        return entity;
    }

    public static FacilityDetailResponse facilityToFacilityDetailResponse(Facility entity, FacilityDetailResponse response) {
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setTaxIdentificationNumber(entity.getTaxIdentificationNumber());
        response.setEmail(entity.getEmail());
        response.setPhone(entity.getPhone());
        response.setCoordinates(new org.springframework.data.geo.Point(entity.getCoordinates().getX(), entity.getCoordinates().getY()));
        return response;
    }
}
