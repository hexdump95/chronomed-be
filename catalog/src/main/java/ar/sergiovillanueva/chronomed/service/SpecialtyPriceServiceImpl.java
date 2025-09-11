package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.SpecialtyPriceRequest;
import ar.sergiovillanueva.chronomed.dto.SpecialtyPriceResponse;
import ar.sergiovillanueva.chronomed.entity.Specialty;
import ar.sergiovillanueva.chronomed.entity.SpecialtyPrice;
import ar.sergiovillanueva.chronomed.mapper.SpecialtyPriceMapper;
import ar.sergiovillanueva.chronomed.repository.SpecialtyPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpecialtyPriceServiceImpl implements SpecialtyPriceService {
    private final Logger log = LoggerFactory.getLogger(SpecialtyPriceServiceImpl.class);
    private final SpecialtyPriceRepository specialtyPriceRepository;

    public SpecialtyPriceServiceImpl(SpecialtyPriceRepository specialtyPriceRepository) {
        this.specialtyPriceRepository = specialtyPriceRepository;
    }

    @Override
    @Transactional
    public SpecialtyPriceResponse save(Long specialtyId, SpecialtyPriceRequest request) {
        log.debug("Request to save SpecialtyPrice : {}", request);
        var specialtyPrice = SpecialtyPriceMapper.specialtyPriceRequestToSpecialtyPrice(request, new SpecialtyPrice());
        var specialty = new Specialty();
        specialty.setId(specialtyId);
        specialtyPrice.setSpecialty(specialty);
        specialtyPriceRepository.save(specialtyPrice);
        return SpecialtyPriceMapper.specialtyPriceToSpecialtyPriceResponse(specialtyPrice, new SpecialtyPriceResponse());
    }
}
