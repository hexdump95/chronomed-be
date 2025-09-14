package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.entity.Specialty;
import ar.sergiovillanueva.chronomed.mapper.SpecialtyMapper;
import ar.sergiovillanueva.chronomed.repository.SpecialtyRepository;
import ar.sergiovillanueva.chronomed.specification.SpecialtySpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class SpecialtyServiceImpl implements SpecialtyService, SpecialtyLookupService {
    private final Logger log = LoggerFactory.getLogger(SpecialtyServiceImpl.class);
    private final SpecialtyRepository specialtyRepository;
    private static final Short PAGE_SIZE = 10;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SpecialtyResponse> findSpecialties(String name, int page) {
        log.debug("Find all specialties with name: {} and page: {}", name, page);
        var specification = SpecialtySpecification.byDeletedAtNull();
        if (name != null && !name.isBlank()) {
            specification = specification.and(SpecialtySpecification.byNameLike(name));
        }

        var pagedSpecialties = specialtyRepository
                .findAll(specification, PageRequest.of(page, PAGE_SIZE))
                .map(x -> SpecialtyMapper.specialtyToSpecialtyResponse(x, new SpecialtyResponse()));

        return new PageResponse.Builder<SpecialtyResponse>()
                .items(pagedSpecialties.getContent())
                .pageIndex(page)
                .pageSize(PAGE_SIZE)
                .totalItems(pagedSpecialties.getTotalElements())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SelectEntityResponse> findAllSpecialties() {
        log.debug("Find all specialties");
        var specification = SpecialtySpecification.byDeletedAtNull();
        var specialties = specialtyRepository.findAll(specification);
        return specialties.stream().map(x -> SpecialtyMapper.specialtyToSelectEntityResponse(x, new SelectEntityResponse())).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SpecialtyDetailResponse findOne(Long id) {
        log.debug("Find specialty with id: {}", id);
        var specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("Specialty with id: " + id + " not found"));
        return SpecialtyMapper.specialtyToSpecialtyDetailResponse(specialty, new SpecialtyDetailResponse());
    }

    @Override
    @Transactional
    public SpecialtyResponse save(SpecialtyRequest request) {
        log.debug("Save specialty with specialty: {}", request);
        var specialty = SpecialtyMapper.specialtyRequestToSpecialty(request, new Specialty());
        specialtyRepository.save(specialty);
        return SpecialtyMapper.specialtyToSpecialtyResponse(specialty, new SpecialtyResponse());
    }

    @Override
    @Transactional
    public SpecialtyResponse update(Long id, SpecialtyRequest request) {
        log.debug("Update specialty with id: {} and specialty: {}", id, request);
        var specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("Specialty with id: " + id + " not found"));

        SpecialtyMapper.specialtyRequestToSpecialty(request, specialty);
        specialtyRepository.save(specialty);
        return SpecialtyMapper.specialtyToSpecialtyResponse(specialty, new SpecialtyResponse());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Delete specialty with id: {}", id);
        var specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("Specialty with id: " + id + " not found"));
        if (specialty.getDeletedAt() != null) {
            throw new RuntimeException();
        }
        specialty.setDeletedAt(Instant.now());
        specialtyRepository.save(specialty);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifyExistingIds(List<Long> ids) {
        log.debug("Verify existing specialties with ids: {}", ids);
        var specification = SpecialtySpecification.byDeletedAtNull();
        specification = specification.and(SpecialtySpecification.byIds(ids));
        long count = specialtyRepository.count(specification);
        return ids.size() == count;
    }

}
