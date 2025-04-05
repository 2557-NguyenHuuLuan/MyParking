package com.example.myparking.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "INVOICE")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_on")
    private Date createOn;

    @Column(name = "total_amount")
    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "contract_id", nullable = false, unique = true)
    private Contract contract;
}
