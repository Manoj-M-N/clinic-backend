package com.clinic.controller;

import com.clinic.dto.PatientMedicalHistoryDTO;
import com.clinic.model.PatientMedicalHistory;
import com.clinic.service.PatientMedicalHistoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medical-history")
@CrossOrigin(origins = "http://localhost:5173")
public class PatientMedicalHistoryController {

    private final PatientMedicalHistoryService service;

    public PatientMedicalHistoryController(
            PatientMedicalHistoryService service
    ) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> saveHistory(
            @RequestBody PatientMedicalHistoryDTO dto
    ) {

        return ResponseEntity.ok(
                service.saveHistory(dto)
        );
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientMedicalHistory> getHistory(
            @PathVariable Integer patientId
    ) {

        return ResponseEntity.ok(
                service.getHistory(patientId)
        );
    }
}