package ar.sergiovillanueva.chronomed.entity;

import jakarta.persistence.*;

@Table(name = "account_facility")
@Entity
public class AccountFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "facility_id")
    private Long facilityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

}
