package com.example.myparking.models;

import jakarta.persistence.*;

@Entity
@Table(name = "RENTAL_PACKAGE")
public class RentalPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of")
    private Integer numberOf;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private RentalForm rentalForm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOf() {
        return numberOf;
    }

    public void setNumberOf(Integer numberOf) {
        this.numberOf = numberOf;
    }

    public RentalForm getRentalForm() {
        return rentalForm;
    }

    public void setRentalForm(RentalForm rentalForm) {
        this.rentalForm = rentalForm;
    }
}