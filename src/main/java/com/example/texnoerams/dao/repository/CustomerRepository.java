package com.example.texnoerams.dao.repository;

import com.example.texnoerams.dao.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    @Override
    @Query(value = "SELECT * FROM customers c WHERE c.id = :id AND c.status IN ('ACTIVE', 'IN_PROGRESS')", nativeQuery = true)
    Optional<CustomerEntity> findById(Long id);

    boolean existsByFin(String fin);
}