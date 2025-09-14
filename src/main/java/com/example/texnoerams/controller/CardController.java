package com.example.texnoerams.controller;

import com.example.texnoerams.dto.request.card.CreateCardRequest;
import com.example.texnoerams.dto.response.CardResponse;
import com.example.texnoerams.service.abstraction.CardService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/v1/card")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CardController {
    CardService cardService;

    @PostMapping("/post")
    public CardResponse createCard(@RequestBody CreateCardRequest request) {
        return cardService.createCard(request);
    }

    @GetMapping("/get/{fin}")
    public List<CardResponse> getCardsByCustomerFin(@PathVariable String fin) {
        return cardService.getCardsByCustomerFin(fin);
    }

    @GetMapping("/getAll")
    public List<CardResponse> getAllCards() {
        return cardService.getAllCards();
    }

    @PutMapping("/changePin/{id}")
    public CardResponse changePin(@PathVariable Long id, @RequestParam String newPin) {
        return cardService.changePin(id, newPin);
    }
}