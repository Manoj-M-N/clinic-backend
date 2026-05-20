package com.clinic.repository;

import com.clinic.model.PharmacyOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyOrderItemRepository
        extends JpaRepository<PharmacyOrderItem, Integer> {
}