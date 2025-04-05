package com.example.myparking.controllers;

import com.example.myparking.dto.ApiResponse;
import com.example.myparking.dto.RegisterVehicleRequest;
import com.example.myparking.models.Vehicle;
import com.example.myparking.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController extends BaseController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerVehicle(
            @RequestBody RegisterVehicleRequest request) {
        Long userId = getCurrentUserId();
        ApiResponse response = vehicleService.registerVehicle(request, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-vehicles")
    public List<Vehicle> getMyParking() {
        Long userId = getCurrentUserId();
        List<Vehicle> myVehicles = vehicleService.getVehiclesByUserId(userId);
        return myVehicles;
    }

}
