package com.clinic.controller;

import com.clinic.dto.PatientEMRDTO;
import com.clinic.service.PatientEMRService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emr")
@CrossOrigin(origins = "http://localhost:5173")
public class PatientEMRController {

    private final PatientEMRService service;

    public PatientEMRController(
            PatientEMRService service
    ) {
        this.service = service;
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientEMRDTO> getEMR(
            @PathVariable Integer patientId
    ) {

        return ResponseEntity.ok(
                service.getPatientEMR(patientId)
        );
    }
}