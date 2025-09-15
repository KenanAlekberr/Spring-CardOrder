package com.example.texnoerams.dto.response;

import com.example.texnoerams.dao.entity.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardResponse {
    Long id;
    String cardNumber;
    LocalDateTime createdAt;
    LocalDateTime expiredAt;
    CustomerEntity customer;
}