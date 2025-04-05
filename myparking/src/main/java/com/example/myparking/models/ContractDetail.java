package com.example.myparking.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "CONTRACT_DETAIL")
public class ContractDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rental_end")
    private LocalDate rentalEnd;

    @Column(name = "rental_start")
    private LocalDate rentalStart;

    @ManyToOne
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "parkingspot_id", nullable = false)
    private ParkingSpot parkingSpot;

    @ManyToOne
    @JoinColumn(name = "rentalpackage_id", nullable = false)
    private RentalPackage rentalPackage;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "rental_end_time")
    private LocalTime rentalEndTime;

    @Column(name = "rental_start_time")
    private LocalTime rentalStartTime;

    @Column(name = "price")
    private Double price;

    @Column(name = "status", nullable = false)
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRentalEnd() {
        return rentalEnd;
    }

    public void setRentalEnd(LocalDate rentalEnd) {
        this.rentalEnd = rentalEnd;
    }

    public LocalDate getRentalStart() {
        return rentalStart;
    }

    public void setRentalStart(LocalDate rentalStart) {
        this.rentalStart = rentalStart;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public RentalPackage getRentalPackage() {
        return rentalPackage;
    }

    public void setRentalPackage(RentalPackage rentalPackage) {
        this.rentalPackage = rentalPackage;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalTime getRentalEndTime() {
        return rentalEndTime;
    }

    public void setRentalEndTime(LocalTime rentalEndTime) {
        this.rentalEndTime = rentalEndTime;
    }

    public LocalTime getRentalStartTime() {
        return rentalStartTime;
    }

    public void setRentalStartTime(LocalTime rentalStartTime) {
        this.rentalStartTime = rentalStartTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
