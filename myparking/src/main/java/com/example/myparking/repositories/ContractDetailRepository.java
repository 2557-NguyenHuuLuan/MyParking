package com.example.myparking.repositories;

import com.example.myparking.models.ContractDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContractDetailRepository extends JpaRepository<ContractDetail, Integer> {
    List<ContractDetail> findByContractId(Long contractId);
    List<ContractDetail> findByParkingSpotId(Long spotId);
    @Query("SELECT c FROM ContractDetail c WHERE c.parkingSpot.id = :spotId " +
            "AND c.rentalStart = :date " +
            "AND c.rentalPackage.rentalForm.form= :form")
    List<ContractDetail> findHourlyContractDetailsOfASpot(@Param("spotId") Long spotId,
                                                          @Param("date") LocalDate Date,
                                                          @Param("form")String form);
}
