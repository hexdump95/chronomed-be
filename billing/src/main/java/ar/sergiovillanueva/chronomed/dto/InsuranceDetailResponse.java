package ar.sergiovillanueva.chronomed.dto;

import java.util.ArrayList;
import java.util.List;

public class InsuranceDetailResponse {
    private Long id;
    private String name;
    private String description;
    private Long insuranceTypeId;
    private String insuranceTypeName;
    private List<InsuranceCoverageResponse> coverages = new ArrayList<>();

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

    public Long getInsuranceTypeId() {
        return insuranceTypeId;
    }

    public void setInsuranceTypeId(Long insuranceTypeId) {
        this.insuranceTypeId = insuranceTypeId;
    }

    public String getInsuranceTypeName() {
        return insuranceTypeName;
    }

    public void setInsuranceTypeName(String insuranceTypeName) {
        this.insuranceTypeName = insuranceTypeName;
    }

    public List<InsuranceCoverageResponse> getCoverages() {
        return coverages;
    }

    public void setCoverages(List<InsuranceCoverageResponse> coverages) {
        this.coverages = coverages;
    }
}
