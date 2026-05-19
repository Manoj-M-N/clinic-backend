package com.clinic.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultations")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer consultationId;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private String symptoms;

    private String diagnosis;

    @Column(length = 2000)
    private String notes;

    private String bloodPressure;

    private String temperature;

    private String pulse;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Integer getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Integer consultationId) {
        this.consultationId = consultationId;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
public String getNotes() {
    return notes;
}

public void setNotes(String notes) {
    this.notes = notes;
}

public String getBloodPressure() {
    return bloodPressure;
}

public void setBloodPressure(String bloodPressure) {
    this.bloodPressure = bloodPressure;
}

public String getTemperature() {
    return temperature;
}

public void setTemperature(String temperature) {
    this.temperature = temperature;
}

public String getPulse() {
    return pulse;
}

public void setPulse(String pulse) {
    this.pulse = pulse;
}

public LocalDateTime getCreatedAt() {
    return createdAt;
}

public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
}
}