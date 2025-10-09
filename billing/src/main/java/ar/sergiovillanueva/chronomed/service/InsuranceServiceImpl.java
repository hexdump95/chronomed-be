package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.entity.Insurance;
import ar.sergiovillanueva.chronomed.entity.Insurance_;
import ar.sergiovillanueva.chronomed.mapper.InsuranceMapper;
import ar.sergiovillanueva.chronomed.repository.InsuranceRepository;
import ar.sergiovillanueva.chronomed.specification.InsuranceSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class InsuranceServiceImpl implements InsuranceService, InsuranceLookupService {
    private final Logger log = LoggerFactory.getLogger(InsuranceServiceImpl.class);
    private final InsuranceRepository insuranceRepository;
    private static final Short PAGE_SIZE = 10;

    public InsuranceServiceImpl(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<InsuranceResponse> findInsurances(String search, int page) {
        log.debug("Find all insurances with name: {} and page: {}", search, page);
        var sort = Sort.by(Sort.Direction.ASC, Insurance_.name.getName());
        var specification = InsuranceSpecification.byDeletedAtNull();
        if (search != null && !search.isBlank()) {
            specification = specification.and(InsuranceSpecification.byNameLike(search));
        }

        var pagedComorbidities = insuranceRepository
                .findAll(specification, PageRequest.of(page, PAGE_SIZE, sort))
                .map(x -> InsuranceMapper.insuranceToInsuranceResponse(x, new InsuranceResponse()));

        return new PageResponse.Builder<InsuranceResponse>()
                .items(pagedComorbidities.getContent())
                .pageIndex(page)
                .pageSize(PAGE_SIZE)
                .totalItems(pagedComorbidities.getTotalElements())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SelectEntityResponse> findAllByInsuranceTypeId(Long insuranceTypeId) {
        log.debug("Find all insurances by insuranceTypeId {}", insuranceTypeId);
        Sort sort = Sort.by(Sort.Direction.ASC, Insurance_.name.getName());
        var specification = InsuranceSpecification.byDeletedAtNull();
        specification = specification.and(InsuranceSpecification.fetchInsuranceType());
        specification = specification.and(InsuranceSpecification.byInsuranceTypeId(insuranceTypeId));
        var insurances = insuranceRepository.findAll(specification, sort);
        return insurances.stream().map(x -> {
            var response = new SelectEntityResponse();
            response.setId(x.getId());
            response.setName(x.getName());
            return response;
        }).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InsuranceResponse> findInsurancesByIds(List<Long> insuranceIds) {
        log.debug("Find all insurances by ids {}", insuranceIds);
        var specification = InsuranceSpecification.byIds(insuranceIds);
        var insurances = insuranceRepository.findAll(specification);
        return insurances.stream().map(x -> {
            var response = new InsuranceResponse();
            response.setId(x.getId());
            response.setName(x.getName());
            response.setInsuranceTypeName(x.getInsuranceType().getName());
            response.setDescription(x.getDescription());
            return response;
        }).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InsuranceDetailResponse getOne(Long id) {
        log.debug("GET request to get insurance by id {}", id);
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("entity not found"));
        return InsuranceMapper.insuranceToInsuranceDetailResponse(insurance, new InsuranceDetailResponse());
    }

    @Override
    @Transactional
    public InsuranceResponse save(InsuranceRequest request) {
        log.debug("Save insurance with body: {}", request);
        var insurance = InsuranceMapper.insuranceRequestToInsurance(request, new Insurance());
        insuranceRepository.save(insurance);
        return InsuranceMapper.insuranceToInsuranceResponse(insurance, new InsuranceResponse());
    }

    @Override
    @Transactional
    public InsuranceResponse update(Long id, InsuranceRequest request) {
        log.debug("Update insurance with id: {} and body: {}", id, request);
        var insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("Insurance with id: " + id + " not found"));

        InsuranceMapper.insuranceRequestToInsurance(request, insurance);
        insuranceRepository.save(insurance);
        return InsuranceMapper.insuranceToInsuranceResponse(insurance, new InsuranceResponse());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Delete insurance with id: {}", id);
        var insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("Insurance with id: " + id + " not found"));
        if (insurance.getDeletedAt() != null) {
            throw new RuntimeException();
        }
        insurance.setDeletedAt(Instant.now());
        insuranceRepository.save(insurance);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifyExistingIds(List<Long> ids) {
        log.debug("Verify existing insurances with ids: {}", ids);
        var specification = InsuranceSpecification.byDeletedAtNull();
        specification = specification.and(InsuranceSpecification.byIds(ids));
        long count = insuranceRepository.count(specification);
        return ids.size() == count;
    }

}
