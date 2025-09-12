package com.example.texnoerams.service.abstraction;

import com.example.texnoerams.dto.request.card.CreateCardRequest;
import com.example.texnoerams.dto.response.CardResponse;

public interface CardService {
    CardResponse createCard(CreateCardRequest request);

    CardResponse changePin(Long cardId, String newPin);
}