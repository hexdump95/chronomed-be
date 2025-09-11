package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.PageResponse;
import ar.sergiovillanueva.chronomed.dto.RoomDetailResponse;
import ar.sergiovillanueva.chronomed.dto.RoomRequest;
import ar.sergiovillanueva.chronomed.dto.RoomResponse;

public interface RoomService {
    PageResponse<RoomResponse> getRoomsByFacilityId(Long facilityId, int pageNumber);

    RoomDetailResponse getRoom(Long id);

    RoomResponse createRoomByFacilityId(Long facilityId, RoomRequest request);

    RoomResponse updateRoom(Long id, RoomRequest request);

    void deleteRoom(Long id);
}
