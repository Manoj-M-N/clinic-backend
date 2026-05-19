package com.clinic.repository;

import com.clinic.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository
        extends JpaRepository<Prescription, Integer> {

    List<Prescription> findByConsultation_ConsultationId(
            Integer consultationId
    );
}