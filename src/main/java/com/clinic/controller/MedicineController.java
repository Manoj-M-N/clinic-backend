package com.clinic.controller;

import com.clinic.model.Medicine;
import com.clinic.service.MedicineService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicine")
@CrossOrigin
public class MedicineController {

    private final MedicineService service;

    public MedicineController(
            MedicineService service
    ) {
        this.service = service;
    }

    // ================= ADD =================
    @PostMapping
    public ResponseEntity<Medicine> addMedicine(
            @RequestBody Medicine medicine
    ) {

        return ResponseEntity.ok(
                service.addMedicine(medicine)
        );
    }

    // ================= GET ALL =================
    @GetMapping
    public ResponseEntity<List<Medicine>>
    getAllMedicines() {

        return ResponseEntity.ok(
                service.getAllMedicines()
        );
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ResponseEntity<Medicine>
    updateMedicine(
            @PathVariable Integer id,
            @RequestBody Medicine medicine
    ) {

        return ResponseEntity.ok(
                service.updateMedicine(
                        id,
                        medicine
                )
        );
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deactivateMedicine(
            @PathVariable Integer id
    ) {

        service.deactivateMedicine(id);

        return ResponseEntity.ok(
                "Medicine deactivated"
        );
    }

    // ================= GET BY ID =================
@GetMapping("/{id}")
public ResponseEntity<Medicine>
getMedicineById(
        @PathVariable Integer id
) {

    Medicine medicine =
            service.getMedicineById(id);

    return ResponseEntity.ok(
            medicine
    );
}
}