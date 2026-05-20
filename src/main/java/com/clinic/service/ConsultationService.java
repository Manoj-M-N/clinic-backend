package com.clinic.service;

import com.clinic.model.Consultation;
import com.clinic.model.Prescription;

import com.clinic.repository.ConsultationRepository;
import com.clinic.repository.PrescriptionRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService {

    private final ConsultationRepository
            consultationRepository;

    private final PrescriptionRepository
            prescriptionRepository;

    private final PharmacyOrderService
            pharmacyOrderService;

    // ================= CONSTRUCTOR =================
    public ConsultationService(

            ConsultationRepository consultationRepository,

            PrescriptionRepository prescriptionRepository,

            PharmacyOrderService pharmacyOrderService

    ) {

        this.consultationRepository =
                consultationRepository;

        this.prescriptionRepository =
                prescriptionRepository;

        this.pharmacyOrderService =
                pharmacyOrderService;
    }

    // ================= SAVE CONSULTATION =================
    public Consultation saveConsultation(

            Consultation consultation,

            List<Prescription> prescriptions

    ) {

        // 🔥 SAVE CONSULTATION
        Consultation savedConsultation =
                consultationRepository.save(
                        consultation
                );

        // 🔥 MAP CONSULTATION TO PRESCRIPTIONS
        for (Prescription p : prescriptions) {

            p.setConsultation(
                    savedConsultation
            );
        }

        // 🔥 SAVE PRESCRIPTIONS
        List<Prescription>
                savedPrescriptions =
                prescriptionRepository.saveAll(
                        prescriptions
                );

        // 🔥 CREATE PHARMACY ORDER
        pharmacyOrderService.createOrder(

                savedConsultation,

                savedPrescriptions
        );

        return savedConsultation;
    }
}