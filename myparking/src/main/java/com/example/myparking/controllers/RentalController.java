package com.example.myparking.controllers;

import com.example.myparking.models.RentalPackage;
import com.example.myparking.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rental")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @GetMapping("/all-packages")
    public List<RentalPackage> allPackage() {
        List<RentalPackage> rentalPackages = rentalService.getRentalPackages();
        return rentalPackages;
    }
}
