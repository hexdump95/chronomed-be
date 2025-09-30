package ar.sergiovillanueva.chronomed.mapper;

import ar.sergiovillanueva.chronomed.dto.RoomDetailResponse;
import ar.sergiovillanueva.chronomed.dto.RoomRequest;
import ar.sergiovillanueva.chronomed.dto.RoomResponse;
import ar.sergiovillanueva.chronomed.entity.Room;
import ar.sergiovillanueva.chronomed.entity.Specialty;

public class RoomMapper {
    public static RoomResponse roomToRoomResponse(Room entity, RoomResponse response) {
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        return response;
    }

    public static RoomDetailResponse roomToRoomDetailResponse(Room entity, RoomDetailResponse response) {
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        var specialtyIds = entity.getSpecialties().stream().map(Specialty::getId).toList();
        response.setSpecialtyIds(specialtyIds);
        return response;
    }

    public static Room roomRequestToRoom(RoomRequest request, Room entity) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        var specialties = request.getSpecialtyIds().stream().map(specialtyId -> {
            var specialty = new Specialty();
            specialty.setId(specialtyId);
            return specialty;
        }).toList();
        entity.getSpecialties().clear();
        entity.getSpecialties().addAll(specialties);
        return entity;
    }

}
