package com.clinic.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDTO {

    private Integer appointmentId;
    private String phone;
    private String gender;
    private String fullName;
    private String doctorName;
    private String disease;     // ✅ FIXED
    private Integer age;        // ✅ FIXED
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;
    private Integer patientId;
    private Integer doctorId;
    private String type;
    private Integer tokenNumber;
    private Integer priority;
    // ✅ CONSTRUCTOR
    public AppointmentDTO(
    Integer appointmentId,
    Integer patientId,
    String fullName,
    String doctorName,
    String disease,
    Integer age,
    LocalDate appointmentDate,
    LocalTime appointmentTime,
    String status,
    String type,
    Integer tokenNumber
) {

    this.appointmentId = appointmentId;
    this.patientId = patientId;
    this.fullName = fullName;
    this.doctorName = doctorName;
    this.disease = disease;
    this.age = age;
    this.appointmentDate = appointmentDate;
    this.appointmentTime = appointmentTime;
    this.status = status;
    this.type = type;
    this.tokenNumber = tokenNumber;
}

    // ✅ GETTERS & SETTERS

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDisease() {   // ✅ FIXED
        return disease;
    }

    public void setDisease(String disease) {   // ✅ FIXED
        this.disease = disease;
    }

    public Integer getAge() {   // ✅ FIXED
        return age;
    }

    public void setAge(Integer age) {   // ✅ FIXED
        this.age = age;
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

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
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

    public String getPhone() {
    return phone;
}

public void setPhone(String phone) {
    this.phone = phone;
}

public String getGender() {
    return gender;
}

public void setGender(String gender) {
    this.gender = gender;
}

public Integer getPriority() {
    return priority;
}

public void setPriority(Integer priority) {
    this.priority = priority;
}


}