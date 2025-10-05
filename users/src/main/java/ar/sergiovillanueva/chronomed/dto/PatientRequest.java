package ar.sergiovillanueva.chronomed.dto;

import java.time.LocalDate;

public class PatientRequest {
    private String identityDocument;
    private Long documentTypeId;
    private LocalDate dateOfBirth;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
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
