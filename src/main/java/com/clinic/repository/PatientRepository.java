package com.clinic.repository;

import com.clinic.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository
        extends JpaRepository<Patient, Long> {

    List<Patient> findByStatus(String status);

    Optional<Patient> findByPhone(String phone);
}