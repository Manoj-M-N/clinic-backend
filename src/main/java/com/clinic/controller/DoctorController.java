package com.clinic.controller;

import com.clinic.model.Appointment;
import com.clinic.model.Doctor;
import com.clinic.security.JwtUtil;
import com.clinic.service.AppointmentService;
import com.clinic.service.DoctorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctor")
@CrossOrigin(origins = "http://localhost:5173")
public class DoctorController {

    private final DoctorService service;
    private final JwtUtil jwtUtil;

    @Autowired
    private AppointmentService appointmentService;

    public DoctorController(DoctorService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    // ✅ CREATE DOCTOR
    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return service.createDoctor(doctor);
    }

    // ✅ GET ALL DOCTORS
    @GetMapping("/all")
    public List<Doctor> getAllDoctors() {
        return service.getAllDoctors();
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Integer id) {
        return service.getDoctorById(id);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public Doctor updateDoctor(@PathVariable Integer id, @RequestBody Doctor doctor) {
        return service.updateDoctor(id, doctor);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Integer id) {
        service.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted");
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Doctor doctor) {

        Doctor result = service.login(doctor.getEmail(), doctor.getPassword());

        if (result == null) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(
                result.getEmail(),
                "DOCTOR",
                result.getDoctorId()
        );

        return ResponseEntity.ok(Map.of("token", token));
    }

    // 🔥 TODAY APPOINTMENTS ONLY
    @GetMapping("/appointments/{doctorId}")
    public ResponseEntity<?> getDoctorAppointments(@PathVariable Integer doctorId) {
        return ResponseEntity.ok(appointmentService.getTodayByDoctorId(doctorId));
    }

    // 🔥 HISTORY (ALL DATA)
    @GetMapping("/appointments/history/{doctorId}")
    public ResponseEntity<?> getDoctorAppointmentsHistory(@PathVariable Integer doctorId) {
        return ResponseEntity.ok(appointmentService.getAllByDoctorId(doctorId));
    }

    

    // 🔥 CALL NEXT
@PutMapping("/queue/next/{doctorId}")
public ResponseEntity<?> callNext(@PathVariable Integer doctorId) {

    Appointment next = appointmentService.callNext(doctorId);

    if (next == null) {
        return ResponseEntity.ok("No patients available");
    }

    return ResponseEntity.ok(next);
}

@GetMapping("/queue/{doctorId}")
public ResponseEntity<?> getQueue(@PathVariable Integer doctorId) {
    return ResponseEntity.ok(
        appointmentService.getQueue(doctorId)
    );
}

}