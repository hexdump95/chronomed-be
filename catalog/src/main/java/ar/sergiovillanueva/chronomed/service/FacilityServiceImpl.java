package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.entity.Facility;
import ar.sergiovillanueva.chronomed.mapper.FacilityMapper;
import ar.sergiovillanueva.chronomed.repository.FacilityRepository;
import ar.sergiovillanueva.chronomed.specification.FacilitySpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {
    private final Logger log = LoggerFactory.getLogger(FacilityServiceImpl.class);
    private final FacilityRepository facilityRepository;
    private static final Short PAGE_SIZE = 10;

    public FacilityServiceImpl(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<FacilityResponse> findFacilities(String name, int page) {
        log.debug("Find all facilities with name: {} and page: {}", name, page);
        var specification = FacilitySpecification.byDeletedAtNull();
        if (name != null && !name.isBlank()) {
            specification = specification.and(FacilitySpecification.byNameLike(name));
        }

        var pagedFacilities = facilityRepository
                .findAll(specification, PageRequest.of(page, PAGE_SIZE))
                .map(x -> FacilityMapper.facilityToFacilityResponse(x, new FacilityResponse()));

        return new PageResponse.Builder<FacilityResponse>()
                .items(pagedFacilities.getContent())
                .pageIndex(page)
                .pageSize(PAGE_SIZE)
                .totalItems(pagedFacilities.getTotalElements())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SelectEntityResponse> findAllFacilities() {
        log.debug("Find all facilities");
        var specification = FacilitySpecification.byDeletedAtNull();
        var facilities = facilityRepository.findAll(specification);
        return facilities.stream().map(x -> FacilityMapper.facilityToSelectEntityResponse(x, new SelectEntityResponse())).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FacilityDetailResponse findOne(Long id) {
        log.debug("Find facility with id: {}", id);
        var facility = facilityRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("Facility with id: " + id + " not found"));
        return FacilityMapper.facilityToFacilityDetailResponse(facility, new FacilityDetailResponse());
    }

    @Override
    @Transactional
    public FacilityResponse save(FacilityRequest request) {
        log.debug("Save facility with facility: {}", request);
        var facility = FacilityMapper.facilityRequestToFacility(request, new Facility());
        facilityRepository.save(facility);
        return FacilityMapper.facilityToFacilityResponse(facility, new FacilityResponse());
    }

    @Override
    @Transactional
    public FacilityResponse update(Long id, FacilityRequest request) {
        log.debug("Update facility with id: {} and facility: {}", id, request);
        var facility = facilityRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("Facility with id: " + id + " not found"));

        FacilityMapper.facilityRequestToFacility(request, facility);
        facilityRepository.save(facility);
        return FacilityMapper.facilityToFacilityResponse(facility, new FacilityResponse());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Delete facility with id: {}", id);
        var facility = facilityRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("Facility with id: " + id + " not found"));
        if (facility.getDeletedAt() != null) {
            throw new RuntimeException();
        }
        facility.setDeletedAt(Instant.now());
        facilityRepository.save(facility);
    }
}
