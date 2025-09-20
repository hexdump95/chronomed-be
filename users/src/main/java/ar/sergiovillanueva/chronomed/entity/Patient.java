package ar.sergiovillanueva.chronomed.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Table(name = "patient")
@Entity
public class Patient extends BaseEntity {
    @Id
    @Column(name = "patient_id")
    private UUID id;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

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

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
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

}
