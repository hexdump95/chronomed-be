package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.SpecialtyPriceRequest;
import ar.sergiovillanueva.chronomed.dto.SpecialtyPriceResponse;
import ar.sergiovillanueva.chronomed.entity.SpecialtyPrice;
import ar.sergiovillanueva.chronomed.mapper.SpecialtyPriceMapper;
import ar.sergiovillanueva.chronomed.repository.SpecialtyPriceRepository;
import ar.sergiovillanueva.chronomed.repository.SpecialtyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpecialtyPriceServiceImpl implements SpecialtyPriceService {
    private final Logger log = LoggerFactory.getLogger(SpecialtyPriceServiceImpl.class);
    private final SpecialtyPriceRepository specialtyPriceRepository;
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyPriceServiceImpl(SpecialtyPriceRepository specialtyPriceRepository, SpecialtyRepository specialtyRepository) {
        this.specialtyPriceRepository = specialtyPriceRepository;
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    @Transactional
    public SpecialtyPriceResponse save(Long specialtyId, SpecialtyPriceRequest request) {
        log.debug("Request to save SpecialtyPrice : {}", request);
        var specialtyPrice = SpecialtyPriceMapper.specialtyPriceRequestToSpecialtyPrice(request, new SpecialtyPrice());
        var specialty = specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new NotFoundServiceException("Specialty not found"));
        specialtyPrice.setSpecialty(specialty);
        specialtyPriceRepository.save(specialtyPrice);
        return SpecialtyPriceMapper.specialtyPriceToSpecialtyPriceResponse(specialtyPrice, new SpecialtyPriceResponse());
    }

    @Override
    public SpecialtyPriceResponse getSpecialtyPrice(Long id) {
        log.debug("Request to get SpecialtyPrice : {}", id);
        var specialtyPrice = specialtyPriceRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("SpecialtyPrice not found with id " + id));
        return SpecialtyPriceMapper.specialtyPriceToSpecialtyPriceResponse(specialtyPrice, new SpecialtyPriceResponse());
    }
}
