package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.SelectEntityResponse;
import ar.sergiovillanueva.chronomed.repository.ProvinceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService {
    private final Logger log = LoggerFactory.getLogger(ProvinceServiceImpl.class);
    private final ProvinceRepository provinceRepository;
    private final ObjectMapper objectMapper;

    public ProvinceServiceImpl(ProvinceRepository provinceRepository, ObjectMapper objectMapper) {
        this.provinceRepository = provinceRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SelectEntityResponse> findAll() {
        log.debug("Find all provinces");
        var provinces = provinceRepository.findAll();
        return objectMapper.convertValue(provinces, objectMapper.getTypeFactory().constructCollectionType(List.class, SelectEntityResponse.class));
    }
}
