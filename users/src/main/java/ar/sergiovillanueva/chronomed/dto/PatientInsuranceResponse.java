package ar.sergiovillanueva.chronomed.dto;

public class PatientInsuranceResponse {
    private int affiliateNumber;
    private Long insuranceId;

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
