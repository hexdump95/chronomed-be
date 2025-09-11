package ar.sergiovillanueva.chronomed.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

public class SpecialtyPriceRequest {

    @NotNull(message = "validFrom should have a value")
    private Instant validFrom;

    private Instant validTo;

    @Positive(message = "Price should be greater than 0")
    private Double price;

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
