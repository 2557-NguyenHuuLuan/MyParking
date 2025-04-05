package com.example.myparking.services;

import com.example.myparking.dto.ApiResponse;
import com.example.myparking.dto.ContractDetailRequest;
import com.example.myparking.models.*;
import com.example.myparking.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;


@Service
public class ContractService {
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private ContractDetailRepository contractDetailRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RentalPackageRepository rentalPackageRepository;
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    public Contract makeContract(Long userId) {
        Contract contract = new Contract();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        contract.setUser(user);
        contract.setValue(0L);
        contract.setPaymentStatus(false);
        contract.setStatus(false);
        contract.setWrittenOn(new Date());
        contractRepository.save(contract);
        return contract;
    }

    public ApiResponse addMoreContractDetails(ContractDetailRequest request, Long userId) {
        try {
            // Liên kết với các entity khác
            ParkingSpot parkingSpot = parkingSpotRepository.findById(request.getParkingSpotId())
                    .orElseThrow(() -> new RuntimeException("Parking spot not found"));
            RentalPackage rentalPackage = rentalPackageRepository.findById(request.getRentalPackageId())
                    .orElseThrow(() -> new RuntimeException("Rental package not found"));
            Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found"));

            ContractDetail contractDetail = new ContractDetail();
            contractDetail.setPrice(priceContractDetail(rentalPackage, vehicle));
            contractDetail.setStatus(0);


            contractDetail.setParkingSpot(parkingSpot);
            contractDetail.setRentalPackage(rentalPackage);
            contractDetail.setVehicle(vehicle);
            contractDetail.setRentalStart(request.getRentalStart());
            contractDetail.setRentalStartTime(request.getRentalStartTime());
            if("HOUR".equals(rentalPackage.getRentalForm().getForm())){
                LocalTime rentalEndTime = request.getRentalStartTime().plusHours(rentalPackage.getNumberOf());
                if (rentalEndTime.isBefore(request.getRentalStartTime())) {
                    contractDetail.setRentalEnd(request.getRentalStart().plusDays(1));
                    contractDetail.setRentalEndTime(rentalEndTime.minusHours(24));
                } else {
                    contractDetail.setRentalEnd(request.getRentalStart());
                    contractDetail.setRentalEndTime(rentalEndTime);
                }
            }else if("DAY".equals(rentalPackage.getRentalForm().getForm())){
                contractDetail.setRentalEnd(request.getRentalStart().plusDays(rentalPackage.getNumberOf()));
                contractDetail.setRentalEndTime(LocalTime.MIDNIGHT);
            }else if("MONTH".equals(rentalPackage.getRentalForm().getForm())){
                contractDetail.setRentalEnd(request.getRentalStart().plusMonths(rentalPackage.getNumberOf()));
                contractDetail.setRentalEndTime(LocalTime.MIDNIGHT);
            }else if("YEAR".equals(rentalPackage.getRentalForm().getForm())){
                contractDetail.setRentalEnd(request.getRentalStart().plusYears(rentalPackage.getNumberOf()));
                contractDetail.setRentalEndTime(LocalTime.MIDNIGHT);
            }

            Contract contract = (request.getContractId() == null)
                    ? makeContract(userId)
                    : contractRepository.findById(request.getContractId())
                    .orElseThrow(() -> new RuntimeException("Contract not found"));

            contractDetail.setContract(contract);

            // Lưu vào database
            contractDetailRepository.save(contractDetail);

            return new ApiResponse(true, "Contract details added successfully.");
        } catch (Exception e) {
            return new ApiResponse(false, "An error occurred: " + e.getMessage());
        }
    }

    public Double priceContractDetail(RentalPackage rentalPackage, Vehicle vehicle){
        Long price = 0L;
        price = (long) (rentalPackage.getNumberOf() * rentalPackage.getRentalForm().getPricePer());
        price = (long) (price * vehicle.getVehicleType().getCoefficient());
        return Double.valueOf(price);
    }
}
