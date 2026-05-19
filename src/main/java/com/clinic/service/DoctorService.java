package com.clinic.service;

import com.clinic.model.Appointment;
import com.clinic.model.Doctor;
import com.clinic.model.Patient;
import com.clinic.model.QueueEntity;
import com.clinic.repository.AppointmentRepository;
import com.clinic.repository.DoctorRepository;
import com.clinic.repository.QueueRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository repo;
    private final QueueRepository queueRepository;
    private final AppointmentRepository appointmentRepository;

    public DoctorService(DoctorRepository repo,
                         QueueRepository queueRepository,
                         AppointmentRepository appointmentRepository) {
        this.repo = repo;
        this.queueRepository = queueRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // ✅ CREATE DOCTOR
    public Doctor createDoctor(Doctor doctor) {
        doctor.setStatus("active");
        doctor.setRole("DOCTOR");
        doctor.setCreatedAt(LocalDateTime.now());
        return repo.save(doctor);
    }

    // ✅ GET ALL
    public List<Doctor> getAllDoctors() {
        return repo.findByStatus("active");
    }

    // ✅ LOGIN
    public Doctor login(String email, String password) {
        Doctor d = repo.findByEmail(email);

        if (d != null && d.getPassword().equals(password)) {
            return d;
        }
        return null;
    }

    // ✅ GET BY ID
    public Doctor getDoctorById(Integer id) {
        return repo.findById(id).orElseThrow();
    }

    // ✅ UPDATE
    public Doctor updateDoctor(Integer id, Doctor updated) {
        Doctor doctor = repo.findById(id).orElseThrow();

        doctor.setDoctorName(updated.getDoctorName());
        doctor.setEmail(updated.getEmail());
        doctor.setPhone(updated.getPhone());
        doctor.setSpecialization(updated.getSpecialization());
        doctor.setConsultationFee(updated.getConsultationFee());
        doctor.setExperience(updated.getExperience());
        doctor.setAvailableStartTime(updated.getAvailableStartTime());
        doctor.setAvailableEndTime(updated.getAvailableEndTime());
        doctor.setStatus(updated.getStatus());

        if (updated.getPassword() != null && !updated.getPassword().isEmpty()) {
            doctor.setPassword(updated.getPassword());
        }

        return repo.save(doctor);
    }

    // ✅ DELETE (SOFT)
    public void deleteDoctor(Integer id) {
        Doctor doctor = repo.findById(id).orElseThrow();
        doctor.setStatus("inactive");
        repo.save(doctor);
    }

    // 🔥 CALL NEXT PATIENT (QUEUE → APPOINTMENT)
    public void callNextPatient(Integer doctorId) {

        // 1️⃣ Get first patient from queue
       QueueEntity first = queueRepository
        .findTopByDoctorIdAndQueueDateOrderByTokenNumberAsc(
                doctorId,
                java.time.LocalDate.now()
        );

        if (first == null) {
    return; // ✅ no crash
}

        // 2️⃣ Create appointment
        Appointment appointment = new Appointment();

        // ✅ SET DOCTOR (RELATION)
        Doctor doctor = new Doctor();
        doctor.setDoctorId(doctorId);
        appointment.setDoctor(doctor);

        // ✅ SET PATIENT (RELATION)
        Patient patient = new Patient();
        patient.setPatientId(first.getPatientId());
        appointment.setPatient(patient);

        // ✅ SET DETAILS
        appointment.setStatus("scheduled");
        appointment.setType("WALK_IN");
        appointment.setTokenNumber(first.getTokenNumber());
        appointment.setAppointmentDate(java.time.LocalDate.now());

        // ⛔ IMPORTANT: TIME (avoid null)
        appointment.setAppointmentTime(java.time.LocalTime.now());

        // ✅ SAVE APPOINTMENT (YOU MISSED THIS)
        appointmentRepository.save(appointment);

        // 3️⃣ REMOVE FROM QUEUE
        queueRepository.delete(first);
    }
}