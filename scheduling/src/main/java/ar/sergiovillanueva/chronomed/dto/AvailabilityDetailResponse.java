package ar.sergiovillanueva.chronomed.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AvailabilityDetailResponse {
    private UUID id;
    private Instant validFrom;
    private Instant validTo;
    private List<AvailabilityDayResponse> availabilityDays = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public List<AvailabilityDayResponse> getAvailabilityDays() {
        return availabilityDays;
    }

    public void setAvailabilityDays(List<AvailabilityDayResponse> availabilityDays) {
        this.availabilityDays = availabilityDays;
    }
}
