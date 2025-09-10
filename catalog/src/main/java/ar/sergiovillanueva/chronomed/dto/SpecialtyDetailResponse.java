package ar.sergiovillanueva.chronomed.dto;

import java.util.ArrayList;
import java.util.List;

public class SpecialtyDetailResponse {
    private Long id;
    private String name;
    private String description;
    private List<SpecialtyPriceResponse> specialtyPrices = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<SpecialtyPriceResponse> getSpecialtyPrices() {
        return specialtyPrices;
    }

    public void setSpecialtyPrices(List<SpecialtyPriceResponse> specialtyPrices) {
        this.specialtyPrices = specialtyPrices;
    }
}
