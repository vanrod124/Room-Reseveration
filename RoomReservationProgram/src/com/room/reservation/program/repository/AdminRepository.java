package com.room.reservation.program.repository;

import com.room.reservation.program.constants.AdminConstants;
import com.room.reservation.program.constants.FileConstants;
import com.room.reservation.program.model.Admin;

import java.io.*;
import java.util.Properties;

public class AdminRepository {

    public Admin getAdmin() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File( AdminConstants.ADMIN_PROPERTIES_FILE )));
        Properties properties = new Properties();
        properties.load( reader );

        reader.close();

        return new Admin( properties.getProperty("admin.name"), properties.getProperty("admin.code"), properties.getProperty("admin.password") );
    }

}
