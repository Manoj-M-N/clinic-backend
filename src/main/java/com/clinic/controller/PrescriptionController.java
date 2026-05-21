package com.clinic.controller;

import com.clinic.model.PharmacyOrder;
import com.clinic.service.PharmacyService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy")
@CrossOrigin
public class PharmacyController {

    private final PharmacyService service;

    public PharmacyController(
            PharmacyService service
    ) {
        this.service = service;
    }

    // ================= GET ALL ORDERS =================

    @GetMapping
    public ResponseEntity<List<PharmacyOrder>>
    getAllOrders() {

        return ResponseEntity.ok(
                service.getAllOrders()
        );
    }
}