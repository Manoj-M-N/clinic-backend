package com.clinic.dto;

import com.clinic.model.Consultation;
import com.clinic.model.Prescription;

import java.util.List;

public class ConsultationRequestDTO {

    private Consultation consultation;

    private List<Prescription> prescriptions;

    // ================= GETTERS & SETTERS =================

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(
            Consultation consultation
    ) {
        this.consultation = consultation;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(
            List<Prescription> prescriptions
    ) {
        this.prescriptions = prescriptions;
    }
}