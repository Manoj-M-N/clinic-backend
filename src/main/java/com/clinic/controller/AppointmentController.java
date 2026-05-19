package com.clinic.controller;

import com.clinic.dto.AppointmentDTO;
import com.clinic.service.AppointmentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appointment")
@CrossOrigin(origins = "http://localhost:5173")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    // ✅ GET ALL
   @GetMapping
public List<AppointmentDTO> getAppointments() {
    return service.getTodayAppointments(); // ✅ ONLY TODAY
}

    // ✅ CREATE
    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentDTO dto) {
        return ResponseEntity.ok(service.createAppointment(dto));
    }

    // ✅ GET SLOTS
    @GetMapping("/slots")
    public Map<String, Object> getSlots(
            @RequestParam Integer doctorId,
            @RequestParam String date) {

        return service.getAvailableSlots(
                doctorId,
                LocalDate.parse(date)
        );
    }

    // ❌ CANCEL
    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelAppointment(@PathVariable Integer id) {
        service.cancelAppointment(id);
        return ResponseEntity.ok("Appointment cancelled");
    }

    // 🚀 START CONSULTATION
    @PutMapping("/start/{id}")
    public ResponseEntity<?> startConsultation(@PathVariable Integer id) {
        service.startConsultation(id);
        return ResponseEntity.ok("Consultation started");
    }

    // 🚀 COMPLETE CONSULTATION
    @PutMapping("/complete/{id}")
    public ResponseEntity<?> completeConsultation(@PathVariable Integer id) {
        service.completeConsultation(id);
        return ResponseEntity.ok("Appointment completed");
    }

    @GetMapping("/history")
    public List<AppointmentDTO> getAllHistory() {
    return service.getAllAppointmentsHistory();
}
}