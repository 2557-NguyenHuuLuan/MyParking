package com.example.myparking.models;

import jakarta.persistence.*;

@Entity
@Table(name = "RENTAL_FORM")
public class RentalForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "form")
    private String form;

    @Column(name = "price_per")
    private Double pricePer;

    @Column(name = "description", length = 1000)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Double getPricePer() {
        return pricePer;
    }

    public void setPricePer(Double pricePer) {
        this.pricePer = pricePer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
