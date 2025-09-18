package ar.sergiovillanueva.chronomed.dto;

import ar.sergiovillanueva.chronomed.entity.Day;

public class AvailabilityDayRequest {
    private String startTime;
    private String endTime;
    private Long facilityId;
    private Day day;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
