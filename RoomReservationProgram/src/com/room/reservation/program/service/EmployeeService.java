package com.room.reservation.program.service;

import com.room.reservation.program.exception.EmployeeNotFoundException;
import com.room.reservation.program.model.Employee;

import java.io.IOException;

public interface EmployeeService {

    boolean isEmployeeExisting( final Long employeeNumber ) throws IOException;

    boolean isEmployeeExisting( final Long employeeNumber, final String employeePassword ) throws IOException;

    void createEmployee( Employee employee );

    void deleteEmployee(Employee employee) throws IOException, EmployeeNotFoundException;
}
