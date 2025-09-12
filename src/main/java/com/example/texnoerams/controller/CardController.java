package com.example.texnoerams.controller;

import com.example.texnoerams.dto.request.card.CreateCardRequest;
import com.example.texnoerams.dto.response.CardResponse;
import com.example.texnoerams.service.abstraction.CardService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/v1/card")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor()
public class CardController {
    CardService cardService;

    @PostMapping("/post")
    public CardResponse createCard(@RequestBody CreateCardRequest request) {
        return cardService.createCard(request);
    }

    @PutMapping("/changePin/{id}")
    public CardResponse changePin(@PathVariable Long id, @RequestParam String newPin) {
        return cardService.changePin(id, newPin);
    }
}