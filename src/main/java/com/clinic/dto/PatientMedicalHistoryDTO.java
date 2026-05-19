package com.clinic.dto;

public class PatientMedicalHistoryDTO {

    private Integer patientId;

    private String allergies;

    private String chronicDiseases;

    private String pastSurgeries;

    private String familyHistory;

    private String bloodGroup;

    private String smokingStatus;

    private String alcoholStatus;

    private Double height;

    private Double weight;

    private String notes;

    // ================= GETTERS =================

    public Integer getPatientId() {
        return patientId;
    }

    public String getAllergies() {
        return allergies;
    }

    public String getChronicDiseases() {
        return chronicDiseases;
    }

    public String getPastSurgeries() {
        return pastSurgeries;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getSmokingStatus() {
        return smokingStatus;
    }

    public String getAlcoholStatus() {
        return alcoholStatus;
    }

    public Double getHeight() {
        return height;
    }

    public Double getWeight() {
        return weight;
    }

    public String getNotes() {
        return notes;
    }

    // ================= SETTERS =================

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public void setChronicDiseases(String chronicDiseases) {
        this.chronicDiseases = chronicDiseases;
    }

    public void setPastSurgeries(String pastSurgeries) {
        this.pastSurgeries = pastSurgeries;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public void setSmokingStatus(String smokingStatus) {
        this.smokingStatus = smokingStatus;
    }

    public void setAlcoholStatus(String alcoholStatus) {
        this.alcoholStatus = alcoholStatus;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}