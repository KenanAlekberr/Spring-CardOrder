package com.example.texnoerams.exception.customer;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(value = CONFLICT)
public class FinLengthException extends RuntimeException {
    public FinLengthException(String message) {
        super(message);
    }
}