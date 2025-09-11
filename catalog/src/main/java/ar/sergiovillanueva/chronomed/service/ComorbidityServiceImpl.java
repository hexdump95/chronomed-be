package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.entity.Comorbidity;
import ar.sergiovillanueva.chronomed.mapper.ComorbidityMapper;
import ar.sergiovillanueva.chronomed.repository.ComorbidityRepository;
import ar.sergiovillanueva.chronomed.specification.ComorbiditySpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
public class ComorbidityServiceImpl implements ComorbidityService {
    private final Logger log = LoggerFactory.getLogger(ComorbidityServiceImpl.class);
    private final ComorbidityRepository comorbidityRepository;
    private static final Short PAGE_SIZE = 10;

    public ComorbidityServiceImpl(ComorbidityRepository comorbidityRepository) {
        this.comorbidityRepository = comorbidityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ComorbidityResponse> findAll(String name, int page) {
        log.debug("Find all comorbidities with name: {} and page: {}", name, page);
        var specification = ComorbiditySpecification.byDeletedAtNull();
        if (name != null && !name.isBlank()) {
            specification = specification.and(ComorbiditySpecification.byNameLike(name));
        }

        var pagedComorbidities = comorbidityRepository
                .findAll(specification, PageRequest.of(page, PAGE_SIZE))
                .map(x -> ComorbidityMapper.comorbidityToComorbidityResponse(x, new ComorbidityResponse()));

        return new PageResponse.Builder<ComorbidityResponse>()
                .items(pagedComorbidities.getContent())
                .pageIndex(page)
                .pageSize(PAGE_SIZE)
                .totalItems(pagedComorbidities.getTotalElements())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ComorbidityResponse findOne(Long id) {
        log.debug("Find comorbidity with id: {}", id);
        var comorbidity = comorbidityRepository.findById(id).orElseThrow(NotFoundServiceException::new);
        return ComorbidityMapper.comorbidityToComorbidityResponse(comorbidity, new ComorbidityResponse());
    }

    @Override
    @Transactional
    public ComorbidityResponse save(ComorbidityRequest request) {
        log.debug("Save comorbidity with body: {}", request);
        var comorbidity = ComorbidityMapper.comorbidityRequestToComorbidity(request, new Comorbidity());
        comorbidityRepository.save(comorbidity);
        return ComorbidityMapper.comorbidityToComorbidityResponse(comorbidity, new ComorbidityResponse());
    }

    @Override
    @Transactional
    public ComorbidityResponse update(Long id, ComorbidityRequest request) {
        log.debug("Update comorbidity with id: {} and body: {}", id, request);
        var comorbidity = comorbidityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comorbidity with id: " + id + " not found"));

        comorbidity.setName(request.getName());
        comorbidity.setDescription(request.getDescription());
        comorbidityRepository.save(comorbidity);
        return ComorbidityMapper.comorbidityToComorbidityResponse(comorbidity, new ComorbidityResponse());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Delete comorbidity with id: {}", id);
        var comorbidity = comorbidityRepository.findById(id)
                .orElseThrow(NotFoundServiceException::new);
        if (comorbidity.getDeletedAt() != null) {
            throw new RuntimeException();
        }
        comorbidity.setDeletedAt(Instant.now());
        comorbidityRepository.save(comorbidity);
    }
}
