package com.clinic.model;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor_login")
public class DoctorLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loginId;

    private int doctorId;

    @Column(unique = true)
    private String email;

    private String password;

    private String role;

    // getters & setters
    public int getLoginId() {
        return loginId;
    }
    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    public int getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
	public String getDoctorName() {
		// TODO Auto-generated method stub
		return null;
	}
}
