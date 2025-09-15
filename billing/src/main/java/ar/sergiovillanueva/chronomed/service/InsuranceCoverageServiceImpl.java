package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.InsuranceCoverageRequest;
import ar.sergiovillanueva.chronomed.dto.InsuranceCoverageResponse;
import ar.sergiovillanueva.chronomed.entity.InsuranceCoverage;
import ar.sergiovillanueva.chronomed.mapper.InsuranceCoverageMapper;
import ar.sergiovillanueva.chronomed.repository.InsuranceCoverageRepository;
import ar.sergiovillanueva.chronomed.repository.InsuranceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InsuranceCoverageServiceImpl implements InsuranceCoverageService {
    private final Logger log = LoggerFactory.getLogger(InsuranceCoverageServiceImpl.class);
    private final InsuranceCoverageRepository insuranceCoverageRepository;
    private final InsuranceRepository insuranceRepository;

    public InsuranceCoverageServiceImpl(InsuranceCoverageRepository insuranceCoverageRepository, InsuranceRepository insuranceRepository) {
        this.insuranceCoverageRepository = insuranceCoverageRepository;
        this.insuranceRepository = insuranceRepository;
    }

    @Override
    @Transactional
    public InsuranceCoverageResponse save(Long insuranceId, InsuranceCoverageRequest request) {
        log.debug("Request to save InsuranceCoverage : {}", request);
        var insuranceCoverage = InsuranceCoverageMapper.insuranceCoverageRequestToInsuranceCoverage(request, new InsuranceCoverage());
        var insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new NotFoundServiceException("Insurance not found"));
        insuranceCoverage.setInsurance(insurance);
        insuranceCoverageRepository.save(insuranceCoverage);
        return InsuranceCoverageMapper.insuranceCoverageToInsuranceCoverageResponse(insuranceCoverage, new InsuranceCoverageResponse());
    }

    @Override
    @Transactional(readOnly = true)
    public InsuranceCoverageResponse getOne(Long id) {
        log.debug("Request to get InsuranceCoverage : {}", id);
        var insuranceCoverage = insuranceCoverageRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("InsuranceCoverage not found with id " + id));
        return InsuranceCoverageMapper.insuranceCoverageToInsuranceCoverageResponse(insuranceCoverage, new InsuranceCoverageResponse());
    }
}
