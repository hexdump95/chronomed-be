package ar.sergiovillanueva.chronomed.dto;

import jakarta.validation.constraints.Positive;

public class InsuranceCoverageRequest extends InstantRangeRequest {

    @Positive(message = "Amount should be greater than 0")
    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
