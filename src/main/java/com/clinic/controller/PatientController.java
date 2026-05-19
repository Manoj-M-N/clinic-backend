package com.clinic.controller;

import com.clinic.model.Patient;
import com.clinic.repository.PatientRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@CrossOrigin(origins = "http://localhost:5173")
public class PatientController {

    private final PatientRepository repo;

    public PatientController(PatientRepository repo) {
        this.repo = repo;
    }

    // GET ALL
    @GetMapping
public List<Patient> getAllPatients() {
    return repo.findByStatus("active"); // ✅ only active
}

    // ADD
    @PostMapping
    public Patient addPatient(@RequestBody Patient patient) {
         patient.setStatus("active");
        return repo.save(patient);
    }

    // ✅ DELETE
   @DeleteMapping("/{id}")
public ResponseEntity<?> deletePatient(@PathVariable Long id) {

    Patient patient = repo.findById(id).orElseThrow();

    patient.setStatus("inactive"); // ✅ SOFT DELETE

    repo.save(patient);

    return ResponseEntity.ok("Patient deactivated");
}

    // ✅ GET ONE (for edit form)
    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
public Patient updatePatient(@PathVariable Long id, @RequestBody Patient updated) {
    Patient patient = repo.findById(id).orElseThrow();

    patient.setFullName(updated.getFullName());
    patient.setPhone(updated.getPhone());
    patient.setGender(updated.getGender());
    patient.setAge(updated.getAge());
    patient.setAdmissionDate(updated.getAdmissionDate());

    return repo.save(patient);
}



}