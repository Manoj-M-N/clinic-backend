package com.clinic.service;

import com.clinic.dto.PatientMedicalHistoryDTO;
import com.clinic.model.Patient;
import com.clinic.model.PatientMedicalHistory;
import com.clinic.repository.PatientMedicalHistoryRepository;
import com.clinic.repository.PatientRepository;

import org.springframework.stereotype.Service;

@Service
public class PatientMedicalHistoryService {

    private final PatientMedicalHistoryRepository historyRepository;
    private final PatientRepository patientRepository;

    public PatientMedicalHistoryService(
            PatientMedicalHistoryRepository historyRepository,
            PatientRepository patientRepository
    ) {
        this.historyRepository = historyRepository;
        this.patientRepository = patientRepository;
    }

    public PatientMedicalHistory saveHistory(
            PatientMedicalHistoryDTO dto
    ) {

        Patient patient = patientRepository.findById(
                Long.valueOf(dto.getPatientId())
        ).orElseThrow(() -> new RuntimeException("Patient not found"));

        PatientMedicalHistory history =
                historyRepository.findByPatient_PatientId(
                        dto.getPatientId()
                ).orElse(new PatientMedicalHistory());

        history.setPatient(patient);

        history.setAllergies(dto.getAllergies());
        history.setChronicDiseases(dto.getChronicDiseases());
        history.setPastSurgeries(dto.getPastSurgeries());
        history.setFamilyHistory(dto.getFamilyHistory());
        history.setBloodGroup(dto.getBloodGroup());
        history.setSmokingStatus(dto.getSmokingStatus());
        history.setAlcoholStatus(dto.getAlcoholStatus());
        history.setHeight(dto.getHeight());
        history.setWeight(dto.getWeight());
        history.setNotes(dto.getNotes());

        return historyRepository.save(history);
    }

    public PatientMedicalHistory getHistory(Integer patientId) {

        return historyRepository.findByPatient_PatientId(patientId)
               .orElse(null);
    }
}