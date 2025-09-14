package com.example.texnoerams.service.abstraction;

import com.example.texnoerams.dto.request.card.CreateCardRequest;
import com.example.texnoerams.dto.response.CardResponse;

import java.util.List;

public interface CardService {
    CardResponse createCard(CreateCardRequest request);

    CardResponse changePin(Long cardId, String newPin);

    List<CardResponse> getAllCards();

    List<CardResponse> getCardsByCustomerFin(String customerFin);
}