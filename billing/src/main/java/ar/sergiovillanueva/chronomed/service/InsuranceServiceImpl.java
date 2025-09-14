package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.SelectEntityResponse;
import ar.sergiovillanueva.chronomed.entity.Insurance_;
import ar.sergiovillanueva.chronomed.repository.InsuranceRepository;
import ar.sergiovillanueva.chronomed.specification.InsuranceSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InsuranceServiceImpl implements InsuranceService {
    private final Logger log = LoggerFactory.getLogger(InsuranceServiceImpl.class);
    private final InsuranceRepository insuranceRepository;
    private final ObjectMapper objectMapper;

    public InsuranceServiceImpl(InsuranceRepository insuranceRepository, ObjectMapper objectMapper) {
        this.insuranceRepository = insuranceRepository;
        this.objectMapper = objectMapper;
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

}
