package ar.sergiovillanueva.chronomed.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "availability")
@Entity
public class Availability extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "valid_from")
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @JoinColumn(name = "account_id")
    private UUID accountId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinTable(
            name = "availability_availability_day",
            joinColumns = @JoinColumn(name = "availability_id"),
            inverseJoinColumns = @JoinColumn(name = "availability_day_id")
    )
    private List<AvailabilityDay> availabilityDays = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public List<AvailabilityDay> getAvailabilityDays() {
        return availabilityDays;
    }

    public void setAvailabilityDays(List<AvailabilityDay> availabilityDays) {
        this.availabilityDays = availabilityDays;
    }
}
