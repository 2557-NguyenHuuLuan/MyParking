package com.example.myparking.controllers;

import com.example.myparking.dto.ApiResponse;
import com.example.myparking.dto.ContractDetailRequest;
import com.example.myparking.models.Contract;
import com.example.myparking.services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contracts")
public class ContractController extends BaseController {

    @Autowired
    private ContractService contractService;

    @PostMapping("/add-details")
    public ResponseEntity<ApiResponse> addContractDetails(
            @RequestBody ContractDetailRequest request
    ) {
        ApiResponse response = contractService.addMoreContractDetails(request, getCurrentUserId());
        return ResponseEntity.ok(response);
    }
}
