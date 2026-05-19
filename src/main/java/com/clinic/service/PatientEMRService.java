package com.clinic.service;

import com.clinic.dto.*;
import com.clinic.model.*;
import com.clinic.repository.*;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientEMRService {

    private final PatientRepository patientRepository;
    private final ConsultationRepository consultationRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PatientMedicalHistoryRepository historyRepository;

    public PatientEMRService(
            PatientRepository patientRepository,
            ConsultationRepository consultationRepository,
            PrescriptionRepository prescriptionRepository,
            PatientMedicalHistoryRepository historyRepository
    ) {

        this.patientRepository = patientRepository;
        this.consultationRepository = consultationRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.historyRepository = historyRepository;
    }

    public PatientEMRDTO getPatientEMR(Integer patientId) {

        Patient patient = patientRepository
                .findById(Long.valueOf(patientId))
                .orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        PatientEMRDTO dto = new PatientEMRDTO();

        dto.setPatientId(patient.getPatientId());
        dto.setFullName(patient.getFullName());
        dto.setGender(patient.getGender());
        dto.setAge(patient.getAge());
        dto.setPhone(patient.getPhone());

        dto.setMedicalHistory(
                historyRepository
                        .findByPatient_PatientId(patientId)
                        .orElse(null)
        );

        List<Consultation> consultations =
                consultationRepository
                        .findByAppointment_Patient_PatientIdOrderByCreatedAtDesc(
                                patientId
                        );

        List<ConsultationHistoryDTO> consultationDTOs =
                consultations.stream().map(c -> {

                    ConsultationHistoryDTO cdto =
                            new ConsultationHistoryDTO();

                    cdto.setConsultationId(
                            c.getConsultationId()
                    );

                    cdto.setSymptoms(c.getSymptoms());
                    cdto.setDiagnosis(c.getDiagnosis());
                    cdto.setNotes(c.getNotes());

                    cdto.setCreatedAt(c.getCreatedAt());

                    cdto.setDoctorName(
                            c.getAppointment()
                                    .getDoctor()
                                    .getDoctorName()
                    );

                    List<Prescription> prescriptions =
                            prescriptionRepository
                                    .findByConsultation_ConsultationId(
                                            c.getConsultationId()
                                    );

                    List<PrescriptionDTO> prescriptionDTOs =
                            prescriptions.stream().map(p -> {

                                PrescriptionDTO pdto =
                                        new PrescriptionDTO();

                                pdto.setMedicineName(
                                        p.getMedicineName()
                                );

                                pdto.setDosage(
                                        p.getDosage()
                                );

                                pdto.setTiming(
                                        p.getTiming()
                                );

                                pdto.setDays(
                                        p.getDays()
                                );

                                pdto.setNotes(
                                        p.getNotes()
                                );

                                return pdto;

                            }).toList();

                    cdto.setPrescriptions(
                            prescriptionDTOs
                    );

                    return cdto;

                }).toList();

        dto.setConsultations(consultationDTOs);

        return dto;
    }
}