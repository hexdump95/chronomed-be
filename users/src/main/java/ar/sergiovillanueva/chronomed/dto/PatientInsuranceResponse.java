package ar.sergiovillanueva.chronomed.dto;

public class PatientInsuranceResponse {
    private Long id;
    private int affiliateNumber;
    private Long insuranceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAffiliateNumber() {
        return affiliateNumber;
    }

    public void setAffiliateNumber(int affiliateNumber) {
        this.affiliateNumber = affiliateNumber;
    }

    public Long getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Long insuranceId) {
        this.insuranceId = insuranceId;
    }
}
