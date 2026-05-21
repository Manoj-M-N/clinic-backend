package com.clinic.service;

import com.clinic.model.PharmacyOrder;
import com.clinic.repository.PharmacyOrderRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PharmacyService {

    private final PharmacyOrderRepository repository;

    public PharmacyService(
            PharmacyOrderRepository repository
    ) {
        this.repository = repository;
    }

    // ================= GET ALL =================

    public List<PharmacyOrder>
    getAllOrders() {

        return repository.findAll();
    }
}