package ar.sergiovillanueva.chronomed.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AvailabilityRequest {
    private Instant validFrom;
    private Instant validTo;
    private List<AvailabilityDayRequest> availabilityDays = new ArrayList<>();

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

    public List<AvailabilityDayRequest> getAvailabilityDays() {
        return availabilityDays;
    }

    public void setAvailabilityDays(List<AvailabilityDayRequest> availabilityDays) {
        this.availabilityDays = availabilityDays;
    }

}
