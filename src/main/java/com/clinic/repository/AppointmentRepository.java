package com.clinic.repository;

import com.clinic.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    // 🔥 SLOT CHECK
    boolean existsByDoctor_DoctorIdAndAppointmentDateAndAppointmentTime(
            Integer doctorId,
            LocalDate date,
            LocalTime time
    );

    // 🔥 GET APPOINTMENTS BY DATE
    List<Appointment> findByDoctor_DoctorIdAndAppointmentDate(
            Integer doctorId,
            LocalDate date
    );

    // 🔥 COUNT WALK-IN TOKENS
    int countByDoctor_DoctorIdAndAppointmentDateAndType(
            Integer doctorId,
            LocalDate date,
            String type
    );

    // 🔥 CUSTOM METHOD
    default int countTodayWalkins(Integer doctorId, LocalDate date) {
        return countByDoctor_DoctorIdAndAppointmentDateAndType(
                doctorId, date, "WALK_IN"
        );
    }

    List<Appointment> findByDoctor_DoctorId(Integer doctorId);

    List<Appointment> findByDoctor_DoctorIdAndTypeOrderByTokenNumberAsc(
    Integer doctorId,
    String type
);

List<Appointment> findByDoctor_DoctorIdAndTypeAndAppointmentDateOrderByTokenNumberAsc(
    Integer doctorId,
    String type,
    LocalDate appointmentDate
);

List<Appointment> findByAppointmentDate(LocalDate date);


List<Appointment> findByDoctor_DoctorIdOrderByAppointmentDateDesc(
        Integer doctorId
);

Appointment findTopByDoctor_DoctorIdAndStatusAndAppointmentDateOrderByPriorityAscTokenNumberAsc(
        Integer doctorId,
        String status,
        LocalDate date
);

@Query("SELECT MAX(a.tokenNumber) FROM Appointment a WHERE a.doctor.doctorId = :doctorId AND a.appointmentDate = :date")
Integer findMaxTokenToday(Integer doctorId, LocalDate date);


}