package com.example.myparking.services;

import com.example.myparking.dto.ApiResponse;
import com.example.myparking.dto.RegisterVehicleRequest;
import com.example.myparking.models.User;
import com.example.myparking.models.Vehicle;
import com.example.myparking.models.VehicleType;
import com.example.myparking.repositories.UserRepository;
import com.example.myparking.repositories.VehicleRepository;
import com.example.myparking.repositories.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    public List<Vehicle> getVehiclesByUserId(Long userId) {
        List<Vehicle> vehicles = vehicleRepository.findByUserId(userId);
        return vehicles;
    }

    public ApiResponse registerVehicle(RegisterVehicleRequest request, Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            VehicleType vehicleType = vehicleTypeRepository.findById(request.getVehicleTypeId())
                    .orElseThrow(() -> new RuntimeException("Vehicle type not found"));

            // Kiểm tra biển số xe đã tồn tại chưa
            if (vehicleRepository.existsByNumberPlate(request.getNumberPlate())) {
                return new ApiResponse(false, "Vehicle with this number plate already exists.");
            }

            Vehicle vehicle = new Vehicle();
            vehicle.setImageUrl(request.getImageUrl());
            vehicle.setNumberPlate(request.getNumberPlate());
            vehicle.setUser(user);
            vehicle.setVehicleType(vehicleType);

            vehicleRepository.save(vehicle);
            return new ApiResponse(true, "Vehicle registered successfully.");
        } catch (RuntimeException e) {
            return new ApiResponse(false, e.getMessage());
        } catch (Exception e) {
            return new ApiResponse(false, "An unexpected error occurred.");
        }
    }


}
