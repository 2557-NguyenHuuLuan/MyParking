package com.example.myparking.repositories;

import com.example.myparking.models.RentalPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalPackageRepository extends JpaRepository<RentalPackage, Long> {
}
