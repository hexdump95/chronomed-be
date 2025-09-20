package ar.sergiovillanueva.chronomed.entity;

import jakarta.persistence.*;

@Table(name = "patient_insurance")
@Entity
public class PatientInsurance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "affiliate_number")
    private int affiliateNumber;

    @Column(name = "insurance_id")
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
