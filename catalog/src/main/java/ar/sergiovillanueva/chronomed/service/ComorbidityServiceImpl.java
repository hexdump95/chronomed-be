package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.entity.Comorbidity;
import ar.sergiovillanueva.chronomed.entity.Comorbidity_;
import ar.sergiovillanueva.chronomed.mapper.ComorbidityMapper;
import ar.sergiovillanueva.chronomed.repository.ComorbidityRepository;
import ar.sergiovillanueva.chronomed.specification.ComorbiditySpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Component
public class ComorbidityServiceImpl implements ComorbidityService, ComorbidityLookupService {
    private final Logger log = LoggerFactory.getLogger(ComorbidityServiceImpl.class);
    private final ComorbidityRepository comorbidityRepository;
    private static final Short PAGE_SIZE = 10;

    public ComorbidityServiceImpl(ComorbidityRepository comorbidityRepository) {
        this.comorbidityRepository = comorbidityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ComorbidityResponse> findComorbidities(String search, int page) {
        log.debug("Find all comorbidities with name: {} and page: {}", search, page);
        Sort sort = Sort.by(Sort.Direction.ASC, Comorbidity_.name.getName());
        var specification = ComorbiditySpecification.byDeletedAtNull();
        if (search != null && !search.isBlank()) {
            specification = specification.and(ComorbiditySpecification.byNameLike(search));
        }

        var pagedComorbidities = comorbidityRepository
                .findAll(specification, PageRequest.of(page, PAGE_SIZE, sort))
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
    public List<SelectEntityResponse> findAllComorbidities() {
        log.debug("Find all comorbidities");
        Sort sort = Sort.by(Sort.Direction.ASC, Comorbidity_.name.getName());
        var specification = ComorbiditySpecification.byDeletedAtNull();
        var comorbidities = comorbidityRepository.findAll(specification, sort);
        return comorbidities.stream().map(x -> ComorbidityMapper.comorbidityToSelectEntityResponse(x, new SelectEntityResponse())).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ComorbidityResponse findOne(Long id) {
        log.debug("Find comorbidity with id: {}", id);
        var comorbidity = comorbidityRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("Comorbidity with id: " + id + " not found"));
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
                .orElseThrow(() -> new NotFoundServiceException("Comorbidity with id: " + id + " not found"));

        ComorbidityMapper.comorbidityRequestToComorbidity(request, comorbidity);
        comorbidityRepository.save(comorbidity);
        return ComorbidityMapper.comorbidityToComorbidityResponse(comorbidity, new ComorbidityResponse());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Delete comorbidity with id: {}", id);
        var comorbidity = comorbidityRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("Comorbidity with id: " + id + " not found"));
        if (comorbidity.getDeletedAt() != null) {
            throw new RuntimeException();
        }
        comorbidity.setDeletedAt(Instant.now());
        comorbidityRepository.save(comorbidity);
    }


    @Override
    @Transactional(readOnly = true)
    public boolean verifyExistingIds(List<Long> ids) {
        log.debug("Verify existing comorbidities with ids: {}", ids);
        var specification = ComorbiditySpecification.byDeletedAtNull();
        specification = specification.and(ComorbiditySpecification.byIds(ids));
        long count = comorbidityRepository.count(specification);
        return ids.size() == count;
    }

}
