package com.room.reservation.program.service;

import com.room.reservation.program.exception.AdminNotFoundException;
import com.room.reservation.program.model.Admin;
import com.room.reservation.program.repository.AdminRepository;

import java.io.IOException;

public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

    public AdminServiceImpl() {
        adminRepository = new AdminRepository();
    }

    @Override
    public boolean isExisting(String adminCode, String adminPassword) throws IOException, AdminNotFoundException {
        Admin admin = adminRepository.getAdmin();

        if( admin == null ) throw new AdminNotFoundException("Admin not found.");

       return adminCode.equalsIgnoreCase(admin.getCode()) && adminPassword.equalsIgnoreCase(admin.getPassword());
    }
}
