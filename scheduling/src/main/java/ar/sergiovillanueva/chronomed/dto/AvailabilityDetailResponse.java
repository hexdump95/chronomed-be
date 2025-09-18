package ar.sergiovillanueva.chronomed.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AvailabilityDetailResponse {
    private Long id;
    private Instant validFrom;
    private Instant validTo;
    private List<AvailabilityDayResponse> availabilityDays = new ArrayList<>();

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

    public List<AvailabilityDayResponse> getAvailabilityDays() {
        return availabilityDays;
    }

    public void setAvailabilityDays(List<AvailabilityDayResponse> availabilityDays) {
        this.availabilityDays = availabilityDays;
    }
}
