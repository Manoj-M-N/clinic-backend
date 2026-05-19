package com.clinic.controller;

import com.clinic.model.Consultation;
import com.clinic.service.ConsultationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultation")
@CrossOrigin(origins = "http://localhost:5173")
public class ConsultationController {

    private final ConsultationService service;

    public ConsultationController(ConsultationService service) {
        this.service = service;
    }

    @PostMapping
    public Consultation save(@RequestBody Consultation consultation) {
        return service.save(consultation);
    }
}