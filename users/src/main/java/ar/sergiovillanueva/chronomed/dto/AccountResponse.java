package ar.sergiovillanueva.chronomed.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountResponse {
    private UUID id;
    private String phoneNumber;
    private String fileNumber;
    private List<Long> facilityIds = new ArrayList<>();
    private List<Long> specialtyIds = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public List<Long> getFacilityIds() {
        return facilityIds;
    }

    public void setFacilityIds(List<Long> facilityIds) {
        this.facilityIds = facilityIds;
    }

    public List<Long> getSpecialtyIds() {
        return specialtyIds;
    }

    public void setSpecialtyIds(List<Long> specialtyIds) {
        this.specialtyIds = specialtyIds;
    }
}
