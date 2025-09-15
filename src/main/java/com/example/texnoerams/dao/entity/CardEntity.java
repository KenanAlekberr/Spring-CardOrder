package com.example.texnoerams.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Data
@Entity
@FieldDefaults(level = PRIVATE)
@Table(name = "cards")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String customerFin;
    String cardNumber;
    String cvv;
    String pin;

    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "expired_at")
    LocalDateTime expiredAt;
}