package ar.sergiovillanueva.chronomed.entity;

import jakarta.persistence.*;

@Table(name = "patient_comorbidity")
@Entity
public class PatientComorbidity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "comorbidity_id")
    private Long comorbidityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getComorbidityId() {
        return comorbidityId;
    }

    public void setComorbidityId(Long comorbidityId) {
        this.comorbidityId = comorbidityId;
    }
}
