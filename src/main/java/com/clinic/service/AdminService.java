package com.clinic.service;

import com.clinic.model.Admin;
import com.clinic.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository repo;

    public AdminService(AdminRepository repo) {
        this.repo = repo;
    }

    public Admin login(String email, String password) {
        Admin admin = repo.findByEmail(email);

        if (admin == null) {
            return null;
        }

        if (!admin.getPassword().equals(password)) {
            return null;
        }

        return admin;
    }
}
