package ar.sergiovillanueva.chronomed.dto;

import ar.sergiovillanueva.chronomed.validations.ValidFromBeforeValidTo;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@ValidFromBeforeValidTo
public class InstantRangeRequest {

    @NotNull(message = "validFrom should have a value")
    private Instant validFrom;

    private Instant validTo;


    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = Instant.parse(validFrom + "T00:00:00.000Z");
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = Instant.parse(validTo + "T23:59:59.999Z");
    }
}
