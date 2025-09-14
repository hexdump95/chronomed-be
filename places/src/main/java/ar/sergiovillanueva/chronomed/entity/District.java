package ar.sergiovillanueva.chronomed.entity;

import jakarta.persistence.*;

@Table(name = "district")
@Entity
public class District extends BaseEntity {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "centroid_latitude")
    private double centroidLatitude;

    @Column(name = "centroid_longitude")
    private double centroidLongitude;

    @ManyToOne
    private Province province;

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

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }
}
