package com.clinic.controller;

import com.clinic.model.Admin;
import com.clinic.model.Doctor;
import com.clinic.repository.DoctorRepository;
import com.clinic.security.JwtUtil;
import com.clinic.service.AdminService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    private final AdminService service;

    private final JwtUtil jwtUtil;

    private final DoctorRepository doctorRepository;

    // ================= CONSTRUCTOR =================
    public AdminController(
            AdminService service,
            JwtUtil jwtUtil,
            DoctorRepository doctorRepository
    ) {

        this.service = service;
        this.jwtUtil = jwtUtil;
        this.doctorRepository = doctorRepository;
    }

    // ================= ADMIN LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Admin admin
    ) {

        Admin result = service.login(
                admin.getEmail(),
                admin.getPassword()
        );

        if (result == null) {

            return ResponseEntity
                    .badRequest()
                    .body("Invalid email or password");
        }

        // ✅ GENERATE JWT
        String token = jwtUtil.generateToken(
                admin.getEmail(),
                "ADMIN",
                0
        );

        // ✅ RETURN RESPONSE
        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "admin", result
                )
        );
    }

    // ================= GET DOCTOR BY ID =================
    @GetMapping("/doctor/{id}")
    public ResponseEntity<Doctor> getDoctorById(
            @PathVariable Integer id
    ) {

        Doctor doctor =
                doctorRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Doctor not found"
                                )
                        );

        return ResponseEntity.ok(doctor);
    }
}