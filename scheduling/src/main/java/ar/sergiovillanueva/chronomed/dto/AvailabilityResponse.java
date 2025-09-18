package ar.sergiovillanueva.chronomed.dto;

import java.time.Instant;

public class AvailabilityResponse {
    private Long id;
    private Instant validFrom;
    private Instant validTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
