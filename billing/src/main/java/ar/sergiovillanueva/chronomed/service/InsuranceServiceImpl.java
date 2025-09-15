package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.entity.Insurance;
import ar.sergiovillanueva.chronomed.entity.Insurance_;
import ar.sergiovillanueva.chronomed.mapper.InsuranceMapper;
import ar.sergiovillanueva.chronomed.repository.InsuranceRepository;
import ar.sergiovillanueva.chronomed.specification.InsuranceSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class InsuranceServiceImpl implements InsuranceService {
    private final Logger log = LoggerFactory.getLogger(InsuranceServiceImpl.class);
    private final InsuranceRepository insuranceRepository;
    private final ObjectMapper objectMapper;
    private static final Short PAGE_SIZE = 10;

    public InsuranceServiceImpl(InsuranceRepository insuranceRepository, ObjectMapper objectMapper) {
        this.insuranceRepository = insuranceRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<InsuranceResponse> findInsurances(String name, int page) {
        log.debug("Find all insurances with name: {} and page: {}", name, page);
        var specification = InsuranceSpecification.byDeletedAtNull();
        if (name != null && !name.isBlank()) {
            specification = specification.and(InsuranceSpecification.byNameLike(name));
        }

        var pagedComorbidities = insuranceRepository
                .findAll(specification, PageRequest.of(page, PAGE_SIZE))
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
        return objectMapper.convertValue(insurances, objectMapper.getTypeFactory().constructCollectionType(List.class, SelectEntityResponse.class));
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
}
