package com.clinic.repository;

import com.clinic.model.PharmacyOrder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyOrderRepository
        extends JpaRepository<
        PharmacyOrder,
        Integer> {
}