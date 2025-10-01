package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.SelectEntityResponse;
import ar.sergiovillanueva.chronomed.entity.InsuranceType_;
import ar.sergiovillanueva.chronomed.repository.InsuranceTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InsuranceTypeServiceImpl implements InsuranceTypeService {
    private final Logger log = LoggerFactory.getLogger(InsuranceTypeServiceImpl.class);
    private final InsuranceTypeRepository insuranceTypeRepository;
    private final ObjectMapper objectMapper;

    public InsuranceTypeServiceImpl(InsuranceTypeRepository insuranceTypeRepository, ObjectMapper objectMapper) {
        this.insuranceTypeRepository = insuranceTypeRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SelectEntityResponse> findAllInsuranceTypes() {
        log.debug("Find all insuranceTypes");
        var sort = Sort.by(Sort.Direction.ASC, InsuranceType_.name.getName());
        var insuranceTypes = insuranceTypeRepository.findAll(sort);
        return objectMapper.convertValue(insuranceTypes, objectMapper.getTypeFactory().constructCollectionType(List.class, SelectEntityResponse.class));
    }
}
