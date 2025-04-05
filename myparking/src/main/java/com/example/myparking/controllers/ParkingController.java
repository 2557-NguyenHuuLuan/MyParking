package com.example.myparking.controllers;

import com.example.myparking.models.ParkingZone;
import com.example.myparking.models.RentalPackage;
import com.example.myparking.services.ParkingService;
import com.example.myparking.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/parking-spots")
public class ParkingController {
    @Autowired
    private ParkingService parkingService;

    @Autowired
    private RentalService rentalService;

    @GetMapping("/parking-spots-valid")
    public ResponseEntity<List<ParkingZone>> getAvailableParkingSpots(
            @RequestParam LocalDate date,
            @RequestParam LocalTime time,
            @RequestParam Long packageId) {
        RentalPackage rentalPackage = rentalService.getRentalPackageById(packageId);
        return ResponseEntity.ok(parkingService.getParkingZoneValid(date, time, rentalPackage));
    }
}
