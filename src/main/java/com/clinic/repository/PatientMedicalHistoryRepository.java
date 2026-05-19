package com.clinic.repository;

import com.clinic.model.PatientMedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientMedicalHistoryRepository
        extends JpaRepository<PatientMedicalHistory, Integer> {

    Optional<PatientMedicalHistory> findByPatient_PatientId(Integer patientId);
}