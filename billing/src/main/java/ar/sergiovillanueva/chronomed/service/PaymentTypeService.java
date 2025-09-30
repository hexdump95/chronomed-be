package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.PaymentTypeResponse;

import java.util.List;

public interface PaymentTypeService {
    List<PaymentTypeResponse> getPaymentTypes();
    void patchPaymentTypeActive(Long id);
}
