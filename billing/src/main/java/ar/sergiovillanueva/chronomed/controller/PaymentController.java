package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.dto.PaymentTypeResponse;
import ar.sergiovillanueva.chronomed.service.PaymentTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final Logger log = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentTypeService paymentTypeService;

    public PaymentController(PaymentTypeService paymentTypeService) {
        this.paymentTypeService = paymentTypeService;
    }

    @GetMapping("/types")
    public List<PaymentTypeResponse> getPaymentTypes() {
        log.debug("GET request to getPaymentTypes");
        return paymentTypeService.getPaymentTypes();
    }
}
