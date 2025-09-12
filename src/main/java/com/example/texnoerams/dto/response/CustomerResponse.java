package com.example.texnoerams.dto.response;

import com.example.texnoerams.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    Long id;
    String firstName;
    String lastName;
    String fin;
    Integer pincode;
    Status status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}