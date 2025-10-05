package ar.sergiovillanueva.chronomed.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "patient")
@Entity
public class Patient extends BaseEntity {
    @Id
    @Column(name = "patient_id")
    private UUID id;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "identity_document")
    private String identityDocument;

    @ManyToOne
    @JoinColumn(name = "document_type_id")
    private DocumentType documentType;

    @ManyToOne
    @JoinColumn(name = "sex_id")
    private Sex sex;

    @ManyToOne
    @JoinColumn(name = "self_perceived_identity_id")
    private SelfPerceivedIdentity selfPerceivedIdentity;

    @Column(name = "created_at")
    private Instant createdAt;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "patient")
    private Domicile domicile;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "patient_patient_comorbidity",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_comorbidity_id")
    )
    private List<PatientComorbidity> patientComorbidities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "patient_patient_insurance",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_insurance_id")
    )
    private List<PatientInsurance> patientInsurances = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdentityDocument() {
        return identityDocument;
    }

    public void setIdentityDocument(String identityDocument) {
        this.identityDocument = identityDocument;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public SelfPerceivedIdentity getSelfPerceivedIdentity() {
        return selfPerceivedIdentity;
    }

    public void setSelfPerceivedIdentity(SelfPerceivedIdentity selfPerceivedIdentity) {
        this.selfPerceivedIdentity = selfPerceivedIdentity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Domicile getDomicile() {
        return domicile;
    }

    public void setDomicile(Domicile domicile) {
        this.domicile = domicile;
    }

    public List<PatientComorbidity> getPatientComorbidities() {
        return patientComorbidities;
    }

    public void setPatientComorbidities(List<PatientComorbidity> patientComorbidities) {
        this.patientComorbidities = patientComorbidities;
    }

    public List<PatientInsurance> getPatientInsurances() {
        return patientInsurances;
    }

    public void setPatientInsurances(List<PatientInsurance> patientInsurances) {
        this.patientInsurances = patientInsurances;
    }
}
