package ar.sergiovillanueva.chronomed.entity;

import jakarta.persistence.*;

@Table(name = "account_specialty")
@Entity
public class AccountSpecialty {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "specialty_id")
    private Long specialtyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Long specialtyId) {
        this.specialtyId = specialtyId;
    }

}
