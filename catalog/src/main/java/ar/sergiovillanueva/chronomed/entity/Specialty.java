package ar.sergiovillanueva.chronomed.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Table(name = "specialty")
@Entity
public class Specialty extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @OneToMany(cascade =  CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "specialty_specialty_price",
            joinColumns = @JoinColumn(name = "specialty_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_price_id")
    )
    private List<SpecialtyPrice> specialtyPrices = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<SpecialtyPrice> getSpecialtyPrices() {
        return specialtyPrices;
    }

    public void setSpecialtyPrices(List<SpecialtyPrice> specialtyPrices) {
        this.specialtyPrices = specialtyPrices;
    }
}
