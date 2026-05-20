package com.clinic.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pharmacy_orders")
public class PharmacyOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt =
            LocalDateTime.now();

    @OneToMany(
            mappedBy = "pharmacyOrder",
            cascade = CascadeType.ALL
    )
    private List<PharmacyOrderItem> items;

    // ================= GETTERS & SETTERS =================

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(
            Consultation consultation
    ) {
        this.consultation = consultation;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(
            Appointment appointment
    ) {
        this.appointment = appointment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt
    ) {
        this.createdAt = createdAt;
    }

    public List<PharmacyOrderItem> getItems() {
        return items;
    }

    public void setItems(
            List<PharmacyOrderItem> items
    ) {
        this.items = items;
    }
}