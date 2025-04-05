package com.example.myparking.services;

import com.example.myparking.models.Card;
import com.example.myparking.models.Contract;
import com.example.myparking.repositories.CardRepository;
import com.example.myparking.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
public class PaymentService {
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private CardRepository cardRepository;

    public void createCard(Contract contract){
        Card card = new Card();
        card.setIssuedDate(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 1);
        card.setExpiryDate(calendar.getTime());
        card.setCardNumber(generateRandomCardNumber());
        card.setContract(contract);
        cardRepository.save(card);
    }

    @Transactional
    public void payContract(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not found"));
        contract.setPaymentStatus(true);
        contract.setStatus(true);
        createCard(contract);
        contractRepository.save(contract);
    }

    private String generateRandomCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            cardNumber.append(random.nextInt(10)); // Tạo số ngẫu nhiên 0-9
        }
        return cardNumber.toString();
    }
}

