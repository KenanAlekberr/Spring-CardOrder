package com.example.texnoerams.service.impl;

import com.example.texnoerams.dao.entity.CardEntity;
import com.example.texnoerams.dao.entity.CustomerEntity;
import com.example.texnoerams.dao.repository.CardRepository;
import com.example.texnoerams.dao.repository.CustomerRepository;
import com.example.texnoerams.dto.request.card.CreateCardRequest;
import com.example.texnoerams.dto.response.CardResponse;
import com.example.texnoerams.exception.card.CardAlreadyException;
import com.example.texnoerams.exception.customer.CustomerNotFoundException;
import com.example.texnoerams.service.abstraction.CardService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor()
public class CardServiceImpl implements CardService {
    CardRepository cardRepository;
    CustomerRepository customerRepository;

    @Override
    public CardResponse createCard(CreateCardRequest request) {
        List<CustomerEntity> customers = customerRepository.findAll();
        CustomerEntity customer = null;

        for (CustomerEntity entity : customers) {
            if (entity.getFin().equals(request.getCustomerFin())) {
                customer = entity;
                break;
            }
        }

        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with FIN: " + request.getCustomerFin());
        }

        List<CardEntity> cards = cardRepository.findAll();

        for (CardEntity card : cards) {
            if (card.getCustomerFin().equals(customer.getFin()))
                throw new CardAlreadyException("Customer already has a card!");
        }

        String cardNumber = generateRandomNumber(16);
        String cvv = generateRandomNumber(3);
        String pin = generatePinFromCardNumber(cardNumber);
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusYears(5);

        CardEntity newCard = CardEntity.builder()
                .customerFin(customer.getFin())
                .cardNumber(cardNumber)
                .cvv(cvv)
                .pin(pin)
                .createdAt(createdAt)
                .expiredAt(expiredAt)
                .build();

        cardRepository.save(newCard);

        return CardResponse.builder()
                .id(newCard.getId())
                .cardNumber(newCard.getCardNumber())
                .cvv(newCard.getCvv())
                .pin(newCard.getPin())
                .createdAt(newCard.getCreatedAt())
                .expiredAt(newCard.getExpiredAt())
                .build();
    }

    @Override
    public CardResponse changePin(Long cardId, String newPin) {
        CardEntity card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setPin(newPin);
        cardRepository.save(card);

        return CardResponse.builder()
                .id(card.getId())
                .cardNumber(card.getCardNumber())
                .cvv(card.getCvv())
                .pin(card.getPin())
                .createdAt(card.getCreatedAt())
                .expiredAt(card.getExpiredAt())
                .build();
    }

    private String generateRandomNumber(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    private String generatePinFromCardNumber(String cardNumber) {
        return cardNumber.substring(cardNumber.length() - 4);
    }
}