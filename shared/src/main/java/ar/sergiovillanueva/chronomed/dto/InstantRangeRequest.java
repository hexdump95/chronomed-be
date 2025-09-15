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

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }
}
