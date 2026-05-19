package com.clinic.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Integer appointmentId;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

	@Column(name = "disease")
	private String disease;


    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "appointment_time")
    private LocalTime appointmentTime;

    private String status;

	@Column(name = "token_number")
	private Integer tokenNumber;

	@Column(name = "type")
	private String type; // WALK_IN / ONLINE

	@Column(name = "priority")
private Integer priority; // 1 = ONLINE, 2 = WALK_IN

	public Integer getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public LocalTime getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(LocalTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public String getType() {
    return type;
}

public void setType(String type) {
    this.type = type;
}

public Integer getTokenNumber() {
    return tokenNumber;
}

public void setTokenNumber(Integer tokenNumber) {
    this.tokenNumber = tokenNumber;
}

public Integer getPriority() {
    return priority;
}

public void setPriority(Integer priority) {
    this.priority = priority;
}

public String getDisease() {
    return disease;
}

public void setDisease(String disease) {
    this.disease = disease;
}
}
