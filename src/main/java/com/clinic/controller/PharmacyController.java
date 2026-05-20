package com.clinic.controller;

import com.clinic.model.PharmacyOrder;
import com.clinic.repository.PharmacyOrderRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy")
@CrossOrigin
public class PharmacyController {

    private final PharmacyOrderRepository
            repository;

    public PharmacyController(
            PharmacyOrderRepository repository
    ) {

        this.repository = repository;
    }

    // ================= PENDING QUEUE =================
    @GetMapping("/pending")
    public ResponseEntity<List<PharmacyOrder>>
    getPendingOrders() {

        return ResponseEntity.ok(

                repository
                        .findByStatusOrderByCreatedAtDesc(
                                "PENDING"
                        )
        );
    }
}