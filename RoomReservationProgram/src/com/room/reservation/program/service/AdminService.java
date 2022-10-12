package com.room.reservation.program.service;

import com.room.reservation.program.exception.AdminNotFoundException;

import java.io.IOException;

public interface AdminService {

    boolean isExisting( final String adminCode, final String adminPassword ) throws IOException, AdminNotFoundException;
}
