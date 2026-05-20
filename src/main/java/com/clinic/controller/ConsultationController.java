package com.clinic.controller;

import com.clinic.dto.ConsultationRequestDTO;
import com.clinic.model.Consultation;

import com.clinic.service.ConsultationService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultation")
@CrossOrigin(origins = "*")
public class ConsultationController {

    private final ConsultationService service;

    public ConsultationController(
            ConsultationService service
    ) {
        this.service = service;
    }

    // ================= SAVE CONSULTATION =================
    @PostMapping
    public Consultation save(

            @RequestBody
            ConsultationRequestDTO request

    ) {

        return service.saveConsultation(

                request.getConsultation(),

                request.getPrescriptions()
        );
    }
}