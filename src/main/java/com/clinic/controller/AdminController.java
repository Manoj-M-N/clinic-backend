package com.clinic.controller;

import com.clinic.dto.LoginRequest; // <-- IMPORT THE NEW DTO
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
@CrossOrigin(
    origins = {
        "http://localhost:5173",
        "https://clinic-management-ui-gamma.vercel.app"
    }
)
public class AdminController {

    private final AdminService service;
    private final JwtUtil jwtUtil;
    private final DoctorRepository doctorRepository;

    public AdminController(AdminService service, JwtUtil jwtUtil, DoctorRepository doctorRepository) {
        this.service = service;
        this.jwtUtil = jwtUtil;
        this.doctorRepository = doctorRepository;
    }

    // ================= ADMIN LOGIN (UPDATED) =================
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest loginRequest // <-- USE THE DTO
    ) {

        // Use the getters from the DTO
        Admin result = service.login(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        if (result == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid email or password");
        }

        // The rest of your code remains the same
        String token = jwtUtil.generateToken(
                result.getEmail(), // Use 'result' here to ensure consistency
                "ADMIN",
                0
        );

        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "admin", result
                )
        );
    }

    // ================= GET DOCTOR BY ID =================
    @GetMapping("/doctor/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Integer id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return ResponseEntity.ok(doctor);
    }
}