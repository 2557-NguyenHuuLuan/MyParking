package com.example.myparking.repositories;

import com.example.myparking.models.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
}
