package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.PageResponse;
import ar.sergiovillanueva.chronomed.dto.SpecialtyDetailResponse;
import ar.sergiovillanueva.chronomed.dto.SpecialtyRequest;
import ar.sergiovillanueva.chronomed.dto.SpecialtyResponse;
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

@Service
public class SpecialtyServiceImpl implements SpecialtyService {
    private final Logger log = LoggerFactory.getLogger(SpecialtyServiceImpl.class);
    private final SpecialtyRepository specialtyRepository;
    private static final Short PAGE_SIZE = 10;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SpecialtyResponse> findAll(String name, int page) {
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
    public SpecialtyDetailResponse findOne(Long id) {
        log.debug("Find specialty with id: {}", id);
        var specialty = specialtyRepository.findById(id).orElseThrow(NotFoundServiceException::new);
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
                .orElseThrow(() -> new RuntimeException("Specialty with id: " + id + " not found"));

        specialty.setName(request.getName());
        specialty.setDescription(request.getDescription());
        specialtyRepository.save(specialty);
        return SpecialtyMapper.specialtyToSpecialtyResponse(specialty, new SpecialtyResponse());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Delete specialty with id: {}", id);
        var specialty = specialtyRepository.findById(id)
                .orElseThrow(NotFoundServiceException::new);
        if (specialty.getDeletedAt() != null) {
            throw new RuntimeException();
        }
        specialty.setDeletedAt(Instant.now());
        specialtyRepository.save(specialty);
    }
}
