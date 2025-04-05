package com.example.myparking.services;

import com.example.myparking.models.RentalPackage;
import com.example.myparking.repositories.RentalPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {
    @Autowired
    private RentalPackageRepository packageRepository;

    public List<RentalPackage> getRentalPackages() {
        return packageRepository.findAll();
    }

    public RentalPackage getRentalPackageById(Long id) {
        return packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental Package not found"));
    }
}
