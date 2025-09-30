package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.service.NotFoundServiceException;
import ar.sergiovillanueva.chronomed.service.FacilityService;
import ar.sergiovillanueva.chronomed.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/facilities")
public class FacilityController {
    private final Logger log = LoggerFactory.getLogger(FacilityController.class);
    private final FacilityService facilityService;
    private final RoomService roomService;

    public FacilityController(FacilityService facilityService, RoomService roomService) {
        this.facilityService = facilityService;
        this.roomService = roomService;
    }

    @GetMapping
    public PageResponse<FacilityResponse> getFacilities(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page
    ) {
        log.debug("GET request to getFacilities");
        return facilityService.findFacilities(search, page);
    }

    @GetMapping("/all")
    public List<SelectEntityResponse> getAllFacilities() {
        log.debug("GET request to getAllFacilities");
        return facilityService.findAllFacilities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacilityDetailResponse> getFacility(@PathVariable Long id) {
        log.debug("GET request to get facility with id {}", id);
        try {
            return ResponseEntity.ok(facilityService.findOne(id));
        } catch (NotFoundServiceException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FacilityResponse> createFacility(@RequestBody FacilityRequest request) {
        log.debug("POST request to createFacility");
        try {
            var facility = facilityService.save(request);
            return ResponseEntity.created(URI.create("/api/v1/facilities/" + facility.getId())).body(facility);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacilityResponse> updateFacility(@PathVariable Long id, @RequestBody FacilityRequest request) {
        log.debug("PUT request to updateFacility");
        try {
            return ResponseEntity.ok(facilityService.update(id, request));
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        log.debug("DELETE request to deleteFacility with id {}", id);
        try {
            facilityService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{facilityId}/rooms")
    public ResponseEntity<PageResponse<RoomResponse>> getRoomsByFacilityId(
            @PathVariable Long facilityId,
            @RequestParam(defaultValue = "0") int page
    ) {
        log.debug("GET request to getRoomsByFacilityId");
        return ResponseEntity.ok(roomService.getRoomsByFacilityId(facilityId, page));
    }

    @PostMapping("/{facilityId}/rooms")
    public ResponseEntity<RoomResponse> createRoomByFacilityId(@PathVariable Long facilityId, @RequestBody RoomRequest request) {
        log.debug("POST request to createRoomByFacilityId");
        try {
            var room = roomService.createRoomByFacilityId(facilityId, request);
            return ResponseEntity.created(URI.create("/api/v1/facilities/rooms/" + room.getId())).body(room);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<RoomDetailResponse> getRoom(@PathVariable Long roomId) {
        log.debug("GET request to getRoom with id {}", roomId);
        try {
            return ResponseEntity.ok(roomService.getRoom(roomId));
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/rooms/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long roomId, @RequestBody RoomRequest request) {
        log.debug("PUT request to updateRoom with id {}", roomId);
        try {
            return ResponseEntity.ok(roomService.updateRoom(roomId, request));
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
        log.debug("DELETE request to deleteRoom with id {}", roomId);
        try {
            roomService.deleteRoom(roomId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
