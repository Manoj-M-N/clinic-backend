
package com.clinic.service;

import com.clinic.model.Prescription;
import com.clinic.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {

    private final PrescriptionRepository repo;

    public PrescriptionService(PrescriptionRepository repo) {
        this.repo = repo;
    }

    public Prescription save(Prescription prescription) {
        return repo.save(prescription);
    }
}