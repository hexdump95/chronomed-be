package ar.sergiovillanueva.chronomed.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "account")
@Entity
public class Account {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "file_number")
    private String fileNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinTable(name = "account_account_facility",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "account_facility_id")
    )
    private List<AccountFacility> facilities = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinTable(name = "account_account_specialty",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "account_specialty_id")
    )
    private List<AccountSpecialty> specialties = new ArrayList<>();

    @Column(name = "locality_id")
    private Long localityId;

    @Column(name = "insurance_ids")
    private List<Long> insuranceIds = new ArrayList<>();

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    public List<AccountFacility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<AccountFacility> facilities) {
        this.facilities = facilities;
    }

    public List<AccountSpecialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<AccountSpecialty> specialties) {
        this.specialties = specialties;
    }

    public Long getLocalityId() {
        return localityId;
    }

    public void setLocalityId(Long localityId) {
        this.localityId = localityId;
    }

    public List<Long> getInsuranceIds() {
        return insuranceIds;
    }

    public void setInsuranceIds(List<Long> insuranceIds) {
        this.insuranceIds = insuranceIds;
    }
}
