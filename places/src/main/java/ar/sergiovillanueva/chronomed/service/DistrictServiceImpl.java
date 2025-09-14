package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.SelectEntityResponse;
import ar.sergiovillanueva.chronomed.repository.DistrictRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {
    private final Logger log = LoggerFactory.getLogger(DistrictServiceImpl.class);
    private final DistrictRepository districtRepository;
    private final ObjectMapper objectMapper;

    public DistrictServiceImpl(DistrictRepository districtRepository, ObjectMapper objectMapper) {
        this.districtRepository = districtRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SelectEntityResponse> findAllByProvinceId(Long provinceId) {
        log.debug("Find all districts by provinceId {}", provinceId);
        var districts = districtRepository.findAllByProvinceId(provinceId);
        return objectMapper.convertValue(districts, objectMapper.getTypeFactory().constructCollectionType(List.class, SelectEntityResponse.class));
    }
}
