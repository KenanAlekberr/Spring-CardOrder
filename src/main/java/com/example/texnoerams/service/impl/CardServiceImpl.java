package com.example.texnoerams.service.impl;

import com.example.texnoerams.dao.entity.CardEntity;
import com.example.texnoerams.dao.entity.CustomerEntity;
import com.example.texnoerams.dao.repository.CardRepository;
import com.example.texnoerams.dto.request.card.CreateCardRequest;
import com.example.texnoerams.dto.response.CardResponse;
import com.example.texnoerams.exception.NotFoundException;
import com.example.texnoerams.service.abstraction.CardService;
import com.example.texnoerams.service.abstraction.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    CardRepository cardRepository;
    CustomerService customerService;

    @Override
    public CardResponse createCard(CreateCardRequest request) {
        CustomerEntity customer = customerService.getCustomerByFin(request.getCustomerFin());
        if (customer == null) throw new NotFoundException("Customer not found by fin: " + request.getCustomerFin());

        CardEntity card = CardEntity.builder().customerFin(customer.getFin()).cardNumber(request.getCardNumber()).cvv(generateRandomNumber(3)).pin(generatePinFromCardNumber(generateRandomNumber(16))).createdAt(LocalDateTime.now()).expiredAt(LocalDateTime.now().plusYears(5)).build();

        cardRepository.save(card);

        return buildCardResponse(card);
    }

    @Override
    public CardResponse changePin(Long cardId, String newPin) {
        CardEntity card = cardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Card not found"));
        CustomerEntity customer = customerService.getCustomerByFin(card.getCustomerFin());

        card.setPin(newPin);
        cardRepository.save(card);

        return CardResponse.builder().id(card.getId()).cardNumber(card.getCardNumber()).createdAt(card.getCreatedAt()).expiredAt(card.getExpiredAt())
                .customer(CustomerEntity.builder()
                        .id(customer.getId())
                        .firstName(customer.getFirstName())
                        .lastName(customer.getLastName())
                        .fin(customer.getFin())
                        .status(customer.getStatus())
                        .createdAt(customer.getCreatedAt())
                        .updatedAt(customer.getUpdatedAt())
                        .build())
                .build();
    }

    @Override
    public List<CardResponse> getAllCards() {
        List<CardEntity> cards = cardRepository.findAll();
        List<CardResponse> responses = new ArrayList<>();

        for (CardEntity card : cards) {
            responses.add(buildCardResponse(card));
        }

        return responses;
    }

    @Override
    public List<CardResponse> getCardsByCustomerFin(String customerFin) {
        List<CardEntity> cards = cardRepository.findAll();
        List<CardResponse> responses = new ArrayList<>();

        for (CardEntity card : cards) {
            if (card.getCustomerFin().equals(customerFin)) responses.add(buildCardResponse(card));
        }

        return responses;
    }

    private String generateRandomNumber(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            stringBuilder.append(random.nextInt(10));
        }

        return stringBuilder.toString();
    }

    private String generatePinFromCardNumber(String cardNumber) {
        return cardNumber.substring(cardNumber.length() - 4);
    }

    private CardResponse buildCardResponse(CardEntity card) {
        CustomerEntity customer = customerService.getCustomerByFin(card.getCustomerFin());

        return CardResponse.builder().id(card.getId()).cardNumber(card.getCardNumber()).createdAt(card.getCreatedAt()).expiredAt(card.getExpiredAt())
                .customer(CustomerEntity.builder()
                        .id(customer.getId())
                        .firstName(customer.getFirstName())
                        .lastName(customer.getLastName())
                        .fin(customer.getFin())
                        .status(customer.getStatus())
                        .createdAt(customer.getCreatedAt())
                        .updatedAt(customer.getUpdatedAt())
                        .build())
                .build();
    }
}