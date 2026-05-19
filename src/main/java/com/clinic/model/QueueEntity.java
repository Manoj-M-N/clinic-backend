package com.clinic.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "queue")
public class QueueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "doctor_id")
    private Integer doctorId;

    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "token_number")
    private Integer tokenNumber;

    @Column(name = "queue_date")
    private LocalDate queueDate;

    // ================= GETTERS =================

    public Integer getId() {
        return id;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getTokenNumber() {
        return tokenNumber;
    }

    // ================= SETTERS =================

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setTokenNumber(Integer tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public LocalDate getQueueDate() {
    return queueDate;
}

public void setQueueDate(LocalDate queueDate) {
    this.queueDate = queueDate;
}
}