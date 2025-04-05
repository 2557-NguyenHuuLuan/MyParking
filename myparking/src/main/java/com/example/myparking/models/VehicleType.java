package com.example.myparking.models;

import jakarta.persistence.*;

@Entity
@Table(name = "VEHICLE_TYPE")
public class VehicleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "typename", nullable = false, unique = true)
    private String typeName;

    @Column(name = "coefficient", nullable = false)
    private Float coefficient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Float getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Float coefficient) {
        this.coefficient = coefficient;
    }
}