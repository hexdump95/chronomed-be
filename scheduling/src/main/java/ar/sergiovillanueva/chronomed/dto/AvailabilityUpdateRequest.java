package ar.sergiovillanueva.chronomed.dto;

import java.util.ArrayList;
import java.util.List;

public class AvailabilityUpdateRequest {
    private List<AvailabilityDayRequest> availabilityDays = new ArrayList<>();

    public List<AvailabilityDayRequest> getAvailabilityDays() {
        return availabilityDays;
    }

    public void setAvailabilityDays(List<AvailabilityDayRequest> availabilityDays) {
        this.availabilityDays = availabilityDays;
    }

}
