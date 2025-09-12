package com.example.texnoerams.exception.card;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(value = CONFLICT)
public class CardAlreadyException extends RuntimeException {
    public CardAlreadyException(String message) {
        super(message);
    }
}