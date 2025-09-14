package ar.sergiovillanueva.chronomed.entity;

import jakarta.persistence.*;

@Table(name = "locality")
@Entity
public class Locality extends BaseEntity {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "centroid_latitude")
    private double centroidLatitude;

    @Column(name = "centroid_longitude")
    private double centroidLongitude;

    @ManyToOne
    private District district;

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

    public double getCentroidLatitude() {
        return centroidLatitude;
    }

    public void setCentroidLatitude(double centroidLatitude) {
        this.centroidLatitude = centroidLatitude;
    }

    public double getCentroidLongitude() {
        return centroidLongitude;
    }

    public void setCentroidLongitude(double centroidLongitude) {
        this.centroidLongitude = centroidLongitude;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}
