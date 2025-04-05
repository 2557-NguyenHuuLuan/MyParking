package com.example.myparking.repositories;

import com.example.myparking.models.ParkingZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingZoneRepository extends JpaRepository<ParkingZone, Long> {
    Optional<ParkingZone> findByName(String name);
}
