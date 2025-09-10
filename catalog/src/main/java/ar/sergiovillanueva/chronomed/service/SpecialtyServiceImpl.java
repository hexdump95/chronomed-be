package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.PageResponse;
import ar.sergiovillanueva.chronomed.dto.SpecialtyRequest;
import ar.sergiovillanueva.chronomed.dto.SpecialtyResponse;
import ar.sergiovillanueva.chronomed.mapper.SpecialtyMapper;
import ar.sergiovillanueva.chronomed.repository.SpecialtyRepository;
import ar.sergiovillanueva.chronomed.specification.SpecialtySpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .map(SpecialtyMapper::toDto);

        return new PageResponse.Builder<SpecialtyResponse>()
                .items(pagedSpecialties.getContent())
                .pageIndex(page)
                .pageSize(PAGE_SIZE)
                .totalItems(pagedSpecialties.getTotalElements())
                .build();
    }

    @Override
    @Transactional
    public SpecialtyResponse save(SpecialtyRequest request) {
        var specialty = SpecialtyMapper.toEntity(request);
        specialty = specialtyRepository.save(specialty);
        return SpecialtyMapper.toDto(specialty);
    }
}
