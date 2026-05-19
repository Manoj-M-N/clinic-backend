package com.clinic.repository;

import com.clinic.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConsultationRepository
        extends JpaRepository<Consultation, Integer> {

List<Consultation> findByAppointment_Patient_PatientIdOrderByCreatedAtDesc(
        Integer patientId);
}