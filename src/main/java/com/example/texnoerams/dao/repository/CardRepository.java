package com.example.texnoerams.dao.repository;

import com.example.texnoerams.dao.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
    @Query(value = "SELECT * FROM customers c WHERE c.fin = :fin AND c.status IN ('ACTIVE', 'IN_PROGRESS')", nativeQuery = true)
    Optional<CardEntity> findByCustomerFin(String fin);
}