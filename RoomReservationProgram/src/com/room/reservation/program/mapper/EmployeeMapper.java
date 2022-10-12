package com.room.reservation.program.mapper;

import com.room.reservation.program.enums.EmploymentStatus;
import com.room.reservation.program.model.Employee;

public class EmployeeMapper {


    public static Employee mapFromText( final String employeeCredentials ) {
        String[] employeeCredsArray = employeeCredentials.trim().split(",");
        // ID,Name,Password,status
        return new Employee(Long.parseLong(employeeCredsArray[0]),employeeCredsArray[1], employeeCredsArray[2] );
    }
}
