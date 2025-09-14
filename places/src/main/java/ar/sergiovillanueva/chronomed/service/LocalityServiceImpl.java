package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.LocalityResponse;
import ar.sergiovillanueva.chronomed.dto.SelectEntityResponse;
import ar.sergiovillanueva.chronomed.repository.LocalityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocalityServiceImpl implements LocalityService {
    private final Logger log = LoggerFactory.getLogger(LocalityServiceImpl.class);
    private final LocalityRepository localityRepository;
    private final ObjectMapper objectMapper;

    public LocalityServiceImpl(LocalityRepository localityRepository, ObjectMapper objectMapper) {
        this.localityRepository = localityRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SelectEntityResponse> findAllByDistrictId(Long districtId) {
        log.debug("Find all localties");
        var localities = localityRepository.findAllByDistrictId(districtId);
        return objectMapper.convertValue(localities, objectMapper.getTypeFactory().constructCollectionType(List.class, SelectEntityResponse.class));
    }

    @Override
    @Transactional(readOnly = true)
    public LocalityResponse getLocality(Long id) {
        log.debug("Find locality by id {}", id);
        var locality = localityRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("locality not found"));
        var localityResponse = new LocalityResponse();
        localityResponse.setId(locality.getId());
        localityResponse.setName(locality.getName());
        localityResponse.setDistrictId(locality.getDistrict().getId());
        localityResponse.setDistrictName(locality.getDistrict().getName());
        localityResponse.setProvinceId(locality.getDistrict().getProvince().getId());
        localityResponse.setProvinceName(locality.getDistrict().getProvince().getName());
        return localityResponse;
    }
}
