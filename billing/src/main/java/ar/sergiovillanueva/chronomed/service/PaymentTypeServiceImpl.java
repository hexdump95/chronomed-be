package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.PaymentTypeResponse;
import ar.sergiovillanueva.chronomed.repository.PaymentTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentTypeServiceImpl implements PaymentTypeService {
    private final PaymentTypeRepository paymentTypeRepository;

    public PaymentTypeServiceImpl(PaymentTypeRepository paymentTypeRepository) {
        this.paymentTypeRepository = paymentTypeRepository;
    }

    @Override
    public List<PaymentTypeResponse> getPaymentTypes() {
        return paymentTypeRepository.findAll().stream().map(p -> {
            var response = new PaymentTypeResponse();
            response.setId(p.getId());
            response.setName(p.getName());
            response.setActive(p.isActive());
            return response;
        }).toList();
    }
}
