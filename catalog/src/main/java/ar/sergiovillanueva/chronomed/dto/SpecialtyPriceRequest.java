package ar.sergiovillanueva.chronomed.dto;

import ar.sergiovillanueva.chronomed.validations.ValidFromBeforeValidTo;
import jakarta.validation.constraints.Positive;

@ValidFromBeforeValidTo
public class SpecialtyPriceRequest extends InstantRangeRequest {

    @Positive(message = "Price should be greater than 0")
    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
