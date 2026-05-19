package com.clinic.repository;

import com.clinic.model.Doctor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Doctor findByEmail(String email); // ✅ REQUIRED
    List<Doctor> findByStatus(String status);
}