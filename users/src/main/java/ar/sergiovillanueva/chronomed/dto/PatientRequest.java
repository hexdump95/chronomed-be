package ar.sergiovillanueva.chronomed.dto;

import java.time.Instant;

public class PatientRequest {
    private String identityDocument;
    private Long documentTypeId;
    private Instant dateOfBirth;
    private Long sexId;
    private Long selfPerceivedIdentityId;

    public String getIdentityDocument() {
        return identityDocument;
    }

    public void setIdentityDocument(String identityDocument) {
        this.identityDocument = identityDocument;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getSexId() {
        return sexId;
    }

    public void setSexId(Long sexId) {
        this.sexId = sexId;
    }

    public Long getSelfPerceivedIdentityId() {
        return selfPerceivedIdentityId;
    }

    public void setSelfPerceivedIdentityId(Long selfPerceivedIdentityId) {
        this.selfPerceivedIdentityId = selfPerceivedIdentityId;
    }
}
