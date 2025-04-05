package com.example.myparking.services;

import com.example.myparking.models.ContractDetail;
import com.example.myparking.models.ParkingSpot;
import com.example.myparking.models.ParkingZone;
import com.example.myparking.models.RentalPackage;
import com.example.myparking.repositories.ContractDetailRepository;
import com.example.myparking.repositories.ParkingZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ParkingService {
    @Autowired
    private ParkingZoneRepository parkingZoneRepository;
    @Autowired
    private ContractDetailRepository contractDetailRepository;


    public ParkingZone getParkingZoneById(Long id) {
        return parkingZoneRepository.findById(id).orElse(null);
    }
    public ParkingZone getParkingZoneByName(String zoneName) {
        return parkingZoneRepository.findByName(zoneName).get();
    }


    public List<ParkingZone> getParkingZoneValid(LocalDate tempStartDate,
                                                 LocalTime tempStartTime,
                                                 RentalPackage selectedRentalPackage) {
        List<ParkingZone> zonesValid = parkingZoneRepository.findAll();
        for (ParkingZone zone : zonesValid) {
            for (ParkingSpot spot : zone.getParkingSpots()) {
                if("HOUR".equals(selectedRentalPackage.getRentalForm().getForm())){
                    spot.setStatus(chechHour(tempStartDate, tempStartTime,spot,selectedRentalPackage));
                }else{
                    spot.setStatus(checkDate(tempStartDate,spot,selectedRentalPackage));
                }
            }
        }
        return zonesValid;
    }

    public int chechHour(LocalDate tempStartDate,
                         LocalTime tempStartTime,
                         ParkingSpot spot,
                         RentalPackage selectedRentalPackage){
        int result = 1;
        List<ContractDetail> hourlyContractDetails = contractDetailRepository.findHourlyContractDetailsOfASpot(spot.getId(),tempStartDate, "HOUR");
        LocalTime tempEndTime = tempStartTime.plusHours(selectedRentalPackage.getNumberOf());
        for(ContractDetail contractDetail : hourlyContractDetails){
            if(contractDetail.getContract().isStatus() == true){
                if(tempStartTime.equals(contractDetail.getRentalStartTime())){
                    result = 0;
                } else if(tempStartTime.isBefore(contractDetail.getRentalStartTime()) && tempEndTime.isAfter(contractDetail.getRentalStartTime())){
                    result = 0;
                }else if(tempStartTime.isAfter(contractDetail.getRentalStartTime()) && tempStartTime.isBefore(contractDetail.getRentalEndTime())){
                    result = 0;
                }
            }

        }
        return result;
    }

    public int checkDate(LocalDate tempStartDate,
                         ParkingSpot spot,
                         RentalPackage selectedRentalPackage){
        int result = 1;
        LocalDate tempEndDate;
        if("DAY".equals(selectedRentalPackage.getRentalForm().getForm())){
            tempEndDate = tempStartDate.plusDays(selectedRentalPackage.getNumberOf());
        } else if("MONTH".equals(selectedRentalPackage.getRentalForm().getForm())){
            tempEndDate = tempStartDate.plusMonths(selectedRentalPackage.getNumberOf());
        }else{
            tempEndDate = tempStartDate.plusYears(selectedRentalPackage.getNumberOf());
        }
        List<ContractDetail> contractDetails = contractDetailRepository.findByParkingSpotId(spot.getId());
        for(ContractDetail contractDetail : contractDetails){
            if(contractDetail.getContract().isStatus() == true){
                if(tempStartDate.equals(contractDetail.getRentalStart())){
                    result = 0;
                } else if(tempStartDate.isBefore(contractDetail.getRentalStart()) && tempEndDate.isAfter(contractDetail.getRentalStart())){
                    result = 0;
                }else if(tempStartDate.isAfter(contractDetail.getRentalStart()) && tempStartDate.isBefore(contractDetail.getRentalEnd())){
                    result = 0;
                }
            }
        }
        return result;
    }
}
