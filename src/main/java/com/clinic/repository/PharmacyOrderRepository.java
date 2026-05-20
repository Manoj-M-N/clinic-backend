package com.clinic.repository;

import com.clinic.model.PharmacyOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PharmacyOrderRepository
        extends JpaRepository<PharmacyOrder, Integer> {

    List<PharmacyOrder> findByStatusOrderByCreatedAtDesc(
            String status
    );
}