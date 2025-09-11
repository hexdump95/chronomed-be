package ar.sergiovillanueva.chronomed.dto;

import java.util.ArrayList;
import java.util.List;

public class RoomRequest {
    private String name;
    private String description;
    private List<Long> specialtyIds = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getSpecialtyIds() {
        return specialtyIds;
    }

    public void setSpecialtyIds(List<Long> specialtyIds) {
        this.specialtyIds = specialtyIds;
    }
}
