package com.clinic.service;

import com.clinic.model.Consultation;
import com.clinic.repository.ConsultationRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsultationService {

    private final ConsultationRepository repo;

    public ConsultationService(ConsultationRepository repo) {
        this.repo = repo;
    }

    public Consultation save(Consultation consultation) {
        return repo.save(consultation);
    }
}