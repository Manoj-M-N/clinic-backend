package com.clinic.service;

import com.clinic.dto.AppointmentDTO;
import com.clinic.model.Appointment;
import com.clinic.model.Doctor;
import com.clinic.model.Patient;
import com.clinic.repository.AppointmentRepository;
import com.clinic.repository.DoctorRepository;
import com.clinic.repository.PatientRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Service    
public class AppointmentService {

    private final AppointmentRepository repo;
    private final DoctorRepository doctorRepo;
    private final PatientRepository patientRepo;


public AppointmentService(
        AppointmentRepository repo,
        DoctorRepository doctorRepo,
        PatientRepository patientRepo
) {
    this.repo = repo;
    this.doctorRepo = doctorRepo;
    this.patientRepo = patientRepo;
}

    // 🔥 GET ALL
    public List<AppointmentDTO> getAllAppointments() {
        updateCompletedAppointments();

       return repo.findAll().stream()
    .map(a -> new AppointmentDTO(
        a.getAppointmentId(),
         a.getPatient().getPatientId(),
        a.getPatient().getFullName(),
        a.getDoctor().getDoctorName(),
        a.getDisease(),
        a.getPatient().getAge(),
        a.getAppointmentDate(),
        a.getAppointmentTime(),
        a.getStatus(),
        a.getType(),
        a.getTokenNumber()
    ))
    .collect(java.util.stream.Collectors.toList());
    }

    // 🔥 AVAILABLE SLOTS (ONLY FOR ONLINE)
    public Map<String, Object> getAvailableSlots(Integer doctorId, LocalDate date) {

        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow();

        LocalTime start = doctor.getAvailableStartTime();
        LocalTime end = doctor.getAvailableEndTime();

        List<String> allSlots = new ArrayList<>();

        LocalTime current = start;

        while (current.isBefore(end)) {
            allSlots.add(current.toString());
            current = current.plusHours(1);
        }

        List<Appointment> appointments =
                repo.findByDoctor_DoctorIdAndAppointmentDate(doctorId, date);

        List<String> booked = appointments.stream()
                .map(a -> a.getAppointmentTime().toString())
                .toList();

        return Map.of(
                "slots", allSlots,
                "booked", booked
        );
    }

    // 🚀 CREATE APPOINTMENT (WALK-IN + ONLINE)
    public Appointment createAppointment(AppointmentDTO dto) {

        // ✅ REQUIRED VALIDATION

if (dto.getFullName() == null || dto.getFullName().trim().isEmpty()) {
    throw new RuntimeException("Patient name is required");
}

if (dto.getPhone() == null || dto.getPhone().trim().isEmpty()) {
    throw new RuntimeException("Phone number is required");
}

if (dto.getDoctorId() == null) {
    throw new RuntimeException("Doctor is required");
}

if (dto.getAppointmentDate() == null) {
    throw new RuntimeException("Appointment date required");
}
    // 🔍 FETCH PATIENT
Patient patient = patientRepo.findByPhone(dto.getPhone())
    .orElseGet(() -> {

        Patient p = new Patient();

        p.setFullName(dto.getFullName().trim());
        p.setPhone(dto.getPhone().trim());

        p.setGender(
            dto.getGender() != null
                ? dto.getGender()
                : "UNKNOWN"
        );

        p.setAge(
            dto.getAge() != null
                ? dto.getAge()
                : 0
        );

        p.setStatus("active");

        return patientRepo.save(p);
    });

    // 🔍 FETCH DOCTOR
    Doctor doctor = doctorRepo
            .findById(dto.getDoctorId())
            .orElseThrow(() -> new RuntimeException("Doctor not found"));

    Appointment appointment = new Appointment();
    appointment.setDisease(dto.getDisease());

    appointment.setPatient(patient);
    appointment.setDoctor(doctor);
    appointment.setAppointmentDate(dto.getAppointmentDate());
    appointment.setStatus("scheduled");
    appointment.setType(dto.getType());

   // COMMON LOGIC (REAL WORLD)

appointment.setAppointmentDate(dto.getAppointmentDate());
appointment.setStatus("scheduled");

// 🔥 TOKEN FOR BOTH
appointment.setTokenNumber(generateToken(dto.getDoctorId()));

// 🔥 TYPE + PRIORITY
if ("ONLINE".equals(dto.getType())) {

    if (dto.getAppointmentTime() == null) {
        throw new RuntimeException("Time required");
    }

    boolean exists = repo.existsByDoctor_DoctorIdAndAppointmentDateAndAppointmentTime(
            dto.getDoctorId(),
            dto.getAppointmentDate(),
            dto.getAppointmentTime()
    );

    if (exists) {
        throw new RuntimeException("Slot already booked ❌");
    }

    appointment.setAppointmentTime(dto.getAppointmentTime());
    appointment.setType("ONLINE");
    appointment.setPriority(1); // 🔥 HIGH PRIORITY

} else {

    appointment.setAppointmentTime(LocalTime.now());
    appointment.setType("WALK_IN");
    appointment.setPriority(2); // 🔥 LOWER PRIORITY
}
    // 💾 SAVE
    return repo.save(appointment);
}

    // 🔥 TOKEN GENERATOR
  public int generateToken(Integer doctorId) {

    Integer max = repo.findMaxTokenToday(doctorId, LocalDate.now());

    return (max == null) ? 1 : max + 1;
}

    // ❌ CANCEL
    public void cancelAppointment(Integer id) {

        Appointment appointment = repo.findById(id).orElseThrow();

        appointment.setStatus("cancelled");

        repo.save(appointment);
    }

    // 🔥 AUTO COMPLETE
    public void updateCompletedAppointments() {
    LocalDate today = LocalDate.now();

    List<Appointment> list = repo.findAll();

    for (Appointment a : list) {
        if (a.getAppointmentDate().isBefore(today)
            && !"cancelled".equals(a.getStatus())
            && !"in_progress".equals(a.getStatus())) {

            a.setStatus("completed");
        }
    }

    repo.saveAll(list);
}

    public void startConsultation(Integer id) {

    Appointment appointment = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Not found"));

    appointment.setStatus("in_progress"); // ✅ EXACT VALUE

    repo.save(appointment);
}

public void completeConsultation(Integer id) {
    Appointment a = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));

    a.setStatus("completed");
    repo.save(a);
}
public List<AppointmentDTO> getTodayByDoctorId(Integer doctorId) {

    LocalDate today = LocalDate.now();

    List<Appointment> list =
            repo.findByDoctor_DoctorIdAndAppointmentDate(doctorId, today);

    return list.stream().map(a ->
            new AppointmentDTO(
                    a.getAppointmentId(),
                     a.getPatient().getPatientId(),
                    a.getPatient().getFullName(),
                    a.getDoctor().getDoctorName(),
                    a.getDisease(),
                    a.getPatient().getAge(),
                    a.getAppointmentDate(),
                    a.getAppointmentTime(),
                    a.getStatus(),
                    a.getType(),
                    a.getTokenNumber()
            )
    ).toList();
}

public List<AppointmentDTO> getQueue(Integer doctorId) {

    LocalDate today = LocalDate.now();

    List<Appointment> list =
        repo.findByDoctor_DoctorIdAndAppointmentDate(doctorId, today)
        .stream()
        .filter(a ->
            "scheduled".equals(a.getStatus()) ||
            "called".equals(a.getStatus())
        )
        .sorted(
            java.util.Comparator
                .comparing(Appointment::getPriority)
                .thenComparing(Appointment::getTokenNumber)
        )
        .toList();

    return list.stream().map(a ->
        new AppointmentDTO(
            a.getAppointmentId(),
             a.getPatient().getPatientId(),
            a.getPatient().getFullName(),
            a.getDoctor().getDoctorName(),
            a.getDisease(),
            a.getPatient().getAge(),
            a.getAppointmentDate(),
            a.getAppointmentTime(),
            a.getStatus(),
            a.getType(),
            a.getTokenNumber()
        )
    ).toList();
}

public List<AppointmentDTO> getTodayAppointments() {

    updateCompletedAppointments();

    List<Appointment> list = repo.findByAppointmentDate(LocalDate.now());

    return list.stream()
        .map(a -> new AppointmentDTO(
            a.getAppointmentId(),
             a.getPatient().getPatientId(),
            a.getPatient().getFullName(),
            a.getDoctor().getDoctorName(),
            a.getDisease(),
            a.getPatient().getAge(),
            a.getAppointmentDate(),
            a.getAppointmentTime(),
            a.getStatus(),
            a.getType(),
            a.getTokenNumber()
        ))
        .toList();
}

public List<AppointmentDTO> getAllAppointmentsHistory() {

    return repo.findAll().stream()
        .map(a -> new AppointmentDTO(
            a.getAppointmentId(),
             a.getPatient().getPatientId(),
            a.getPatient().getFullName(),
            a.getDoctor().getDoctorName(),
            a.getDisease(),
            a.getPatient().getAge(),
            a.getAppointmentDate(),
            a.getAppointmentTime(),
            a.getStatus(),
            a.getType(),
            a.getTokenNumber()
        ))
        .toList();
}
public List<AppointmentDTO> getAllByDoctorId(Integer doctorId) {

    List<Appointment> list =
            repo.findByDoctor_DoctorIdOrderByAppointmentDateDesc(doctorId);

    return list.stream().map(a ->
            new AppointmentDTO(
                    a.getAppointmentId(),
                     a.getPatient().getPatientId(),
                    a.getPatient().getFullName(),
                    a.getDoctor().getDoctorName(),
                    a.getDisease(),
                    a.getPatient().getAge(),
                    a.getAppointmentDate(),
                    a.getAppointmentTime(),
                    a.getStatus(),
                    a.getType(),
                    a.getTokenNumber()
            )
    ).toList();
}

public Appointment callNext(Integer doctorId) {

    LocalDate today = LocalDate.now();

    // BLOCK if active patient exists
    boolean activeExists =
        repo.findByDoctor_DoctorIdAndAppointmentDate(doctorId, today)
        .stream()
        .anyMatch(a ->
            "called".equals(a.getStatus()) ||
            "in_progress".equals(a.getStatus())
        );

    if (activeExists) {
        throw new RuntimeException(
            "Complete current patient first"
        );
    }

    // NEXT PATIENT
    List<Appointment> nextList =
        repo.findByDoctor_DoctorIdAndAppointmentDate(doctorId, today)
        .stream()
        .filter(a -> "scheduled".equals(a.getStatus()))
        .sorted(
            java.util.Comparator
                .comparing(Appointment::getPriority)
                .thenComparing(Appointment::getTokenNumber)
        )
        .toList();

    if (nextList.isEmpty()) {
        return null;
    }

    Appointment next = nextList.get(0);

    next.setStatus("called");

    return repo.save(next);
}
}