package com.clinic.dto;

// This is a simple class to hold only the data needed for login.
public class LoginRequest {
    private String email;
    private String password;

    // Getters and Setters are required for Spring to map the JSON
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
}