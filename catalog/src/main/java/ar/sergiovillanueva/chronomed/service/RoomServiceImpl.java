package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.PageResponse;
import ar.sergiovillanueva.chronomed.dto.RoomDetailResponse;
import ar.sergiovillanueva.chronomed.dto.RoomRequest;
import ar.sergiovillanueva.chronomed.dto.RoomResponse;
import ar.sergiovillanueva.chronomed.entity.Room;
import ar.sergiovillanueva.chronomed.entity.Room_;
import ar.sergiovillanueva.chronomed.mapper.RoomMapper;
import ar.sergiovillanueva.chronomed.repository.FacilityRepository;
import ar.sergiovillanueva.chronomed.repository.RoomRepository;
import ar.sergiovillanueva.chronomed.specification.RoomSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class RoomServiceImpl implements RoomService {
    private final Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);
    private final RoomRepository roomRepository;
    private final FacilityRepository facilityRepository;
    private static final Short PAGE_SIZE = 10;

    public RoomServiceImpl(RoomRepository roomRepository, FacilityRepository facilityRepository) {
        this.roomRepository = roomRepository;
        this.facilityRepository = facilityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<RoomResponse> getRoomsByFacilityId(Long facilityId, int page) {
        log.debug("Find all rooms by facilityId: {} with page: {}", facilityId, page);
        var sort = Sort.by(Sort.Direction.ASC, Room_.name.getName());
        var specification = RoomSpecification.byDeletedAtNull();

        var pagedRooms = roomRepository
                .findAll(specification, PageRequest.of(page, PAGE_SIZE, sort))
                .map(x -> RoomMapper.roomToRoomResponse(x, new RoomResponse()));

        return new PageResponse.Builder<RoomResponse>()
                .items(pagedRooms.getContent())
                .pageIndex(page)
                .pageSize(PAGE_SIZE)
                .totalItems(pagedRooms.getTotalElements())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public RoomDetailResponse getRoom(Long id) {
        log.debug("Find room by id: {}", id);
        var room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("Room with id " + id + " not found"));
        return RoomMapper.roomToRoomDetailResponse(room, new RoomDetailResponse());
    }

    @Override
    @Transactional
    public RoomResponse createRoomByFacilityId(Long facilityId, RoomRequest request) {
        log.debug("Create room by facilityId: {}", facilityId);
        var room = RoomMapper.roomRequestToRoom(request, new Room());
        var facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new NotFoundServiceException("Facility not found"));
        room.setFacility(facility);
        roomRepository.save(room);
        return RoomMapper.roomToRoomResponse(room, new RoomResponse());
    }

    @Override
    @Transactional
    public RoomResponse updateRoom(Long id, RoomRequest request) {
        log.debug("Update room by id: {}", id);
        var room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("Room with id: " + id + " not found"));

        RoomMapper.roomRequestToRoom(request, room);
        roomRepository.save(room);
        return RoomMapper.roomToRoomResponse(room, new RoomResponse());
    }

    @Override
    @Transactional
    public void deleteRoom(Long id) {
        log.debug("Delete room by id: {}", id);
        var room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("Room with id: " + id + " not found"));

        if (room.getDeletedAt() != null) {
            throw new RuntimeException("Room with id: " + id + " has been deleted");
        }
        room.setDeletedAt(Instant.now());
        roomRepository.save(room);
    }
}
