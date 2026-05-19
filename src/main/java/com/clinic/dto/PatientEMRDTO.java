package com.clinic.dto;

import java.util.List;

public class PatientEMRDTO {

    private Integer patientId;

    private String fullName;

    private String gender;

    private Integer age;

    private String phone;

    private Object medicalHistory;

    private List<ConsultationHistoryDTO> consultations;

    // ================= GETTERS & SETTERS =================

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(Object medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public List<ConsultationHistoryDTO> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<ConsultationHistoryDTO> consultations) {
        this.consultations = consultations;
    }
}