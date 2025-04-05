package com.example.myparking.repositories;

import com.example.myparking.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByNumberPlate(String numberPlate);
    List<Vehicle> findByUserId(Long userId);
}
