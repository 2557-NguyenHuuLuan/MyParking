package com.example.myparking.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ContractDetailRequest {
    private LocalDate rentalStart;
    private LocalTime rentalStartTime;
    private Long parkingSpotId;
    private Long rentalPackageId;
    private Long vehicleId;
    private Long contractId;

    public ContractDetailRequest(
                                 LocalDate rentalStart,
                                 LocalTime rentalStartTime,
                                 Long parkingSpotId,
                                 Long rentalPackageId,
                                 Long vehicleId,
                                 Long contractId) {
        this.rentalStart = rentalStart;
        this.rentalStartTime = rentalStartTime;
        this.parkingSpotId = parkingSpotId;
        this.rentalPackageId = rentalPackageId;
        this.vehicleId = vehicleId;
        this.contractId = contractId;
    }

    public LocalDate getRentalStart() {
        return rentalStart;
    }

    public void setRentalStart(LocalDate rentalStart) {
        this.rentalStart = rentalStart;
    }

    public LocalTime getRentalStartTime() {
        return rentalStartTime;
    }

    public void setRentalStartTime(LocalTime rentalStartTime) {
        this.rentalStartTime = rentalStartTime;
    }

    public Long getParkingSpotId() {
        return parkingSpotId;
    }

    public void setParkingSpotId(Long parkingSpotId) {
        this.parkingSpotId = parkingSpotId;
    }

    public Long getRentalPackageId() {
        return rentalPackageId;
    }

    public void setRentalPackageId(Long rentalPackageId) {
        this.rentalPackageId = rentalPackageId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
    public Long getContractId() {
        return contractId;
    }
    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }
}
