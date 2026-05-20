package com.clinic.service;

import com.clinic.model.Medicine;
import com.clinic.repository.MedicineRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {

    private final MedicineRepository repository;

    public MedicineService(
            MedicineRepository repository
    ) {
        this.repository = repository;
    }

    // ================= ADD =================
    public Medicine addMedicine(
            Medicine medicine
    ) {

        medicine.setStatus("ACTIVE");

        return repository.save(medicine);
    }

    // ================= GET ALL =================
    public List<Medicine> getAllMedicines() {

        return repository.findAll();
    }

    // ================= GET ACTIVE =================
    public List<Medicine> getActiveMedicines() {

        return repository.findByStatus(
                "ACTIVE"
        );
    }

    // ================= UPDATE =================
    public Medicine updateMedicine(
            Integer id,
            Medicine updated
    ) {

        Medicine medicine =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Medicine not found"
                                ));

        medicine.setMedicineName(
                updated.getMedicineName()
        );

        medicine.setCategory(
                updated.getCategory()
        );

        medicine.setManufacturer(
                updated.getManufacturer()
        );

        medicine.setStockQuantity(
                updated.getStockQuantity()
        );

        medicine.setUnitPrice(
                updated.getUnitPrice()
        );

        medicine.setExpiryDate(
                updated.getExpiryDate()
        );

        return repository.save(medicine);
    }

    // ================= DELETE =================
    public void deactivateMedicine(
            Integer id
    ) {

        Medicine medicine =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Medicine not found"
                                ));

        medicine.setStatus("INACTIVE");

        repository.save(medicine);
    }

    public Medicine getMedicineById(
        Integer id
) {

    return repository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Medicine not found"
                    ));
}

}