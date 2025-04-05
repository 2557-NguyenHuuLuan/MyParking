package com.example.myparking.controllers;

import com.example.myparking.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/payment/")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay/{contractId}")
    public ResponseEntity<String> payContract(@PathVariable Long contractId) {
        try {
            paymentService.payContract(contractId);
            return ResponseEntity.ok("Payment successful for contract ID: " + contractId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Payment failed: " + e.getMessage());
        }
    }
}
